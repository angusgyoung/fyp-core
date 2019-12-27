package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.profile.Profile;
import io.dotwave.isysserver.security.jwt.JwtRequest;
import io.dotwave.isysserver.security.jwt.JwtResponse;
import io.dotwave.isysserver.security.jwt.JwtTokenUtil;
import io.dotwave.isysserver.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

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

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // get the users details to return with the token
        Profile profile = profileRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, profile));
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
