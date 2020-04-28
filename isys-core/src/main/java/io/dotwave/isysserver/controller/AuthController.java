package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.error.ValidationException;
import io.dotwave.isysserver.model.profile.Profile;
import io.dotwave.isysserver.security.jwt.JwtRequest;
import io.dotwave.isysserver.security.jwt.JwtResponse;
import io.dotwave.isysserver.security.jwt.JwtTokenUtil;
import io.dotwave.isysserver.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // The minimum time in seconds before token expiry that a client can
    // request a new token. By default this will be 30 seconds
    @Value("${jwt.refreshThresholdSeconds:30}")
    private int refreshThresholdSeconds;

    private AuthenticationManager authenticationManager;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private ProfileRepository profileRepository;

    public AuthController(@Autowired AuthenticationManager authenticationManager,
                          @Autowired JwtUserDetailsService jwtUserDetailsService,
                          @Autowired JwtTokenUtil jwtTokenUtil,
                          @Autowired ProfileRepository profileRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.profileRepository = profileRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // get the users details to return with the token
        Profile profile = profileRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, profile, jwtTokenUtil.getExpirationTimestampFromToken(token)));
    }

    // Generate a new JWT token for a client when the TTL is within the
    // next 30 seconds
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String tokenHeader) {
        // This endpoint is secured so we can be sure that the token is valid
        long currentTimestamp = System.currentTimeMillis() / 1000L;
        long expiryTimestamp = jwtTokenUtil.getExpirationTimestampFromToken(tokenHeader.substring(7));
        if (expiryTimestamp - currentTimestamp < refreshThresholdSeconds) {
            UserDetails userDetails =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            final String token = jwtTokenUtil.generateToken(userDetails);
            final long newTokenExpiry = jwtTokenUtil.getExpirationTimestampFromToken(token);

            return ResponseEntity.ok(new JwtResponse(token, newTokenExpiry));
        }
        throw new ValidationException(String.format("Refresh token not available until %s seconds before expiry", refreshThresholdSeconds));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");

        if (profileRepository.existsByUsername(username)) {
            throw new ValidationException("Username already exists");
        }
        String password = body.get("password");

        // tinygraphs currently doesn't support https. This change is pending in
        // https://github.com/taironas/tinygraphs/issues/101, but for now we will
        // just use http. This will cause most browsers to mark the site as unsecure.
        Profile profile = profileRepository.save(new Profile(
                username,
                password,
                String.format("http://tinygraphs.com/spaceinvaders/%s?theme=duskfalling&numcolors=4&size=300&fmt=svg", username)
        ));
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(profile.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(token, profile, jwtTokenUtil.getExpirationTimestampFromToken(token)));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
