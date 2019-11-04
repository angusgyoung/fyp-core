package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.profile.Auth0User;
import io.dotwave.isysserver.model.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(@Autowired ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) {
        if (profileRepository.existsByUser_Username(username))
            return ResponseEntity.ok(profileRepository.findByUser_Username(username));
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    // will be called by auth0 after a user registers, usually you would
    // allow them to store all metadata about users but for the purpose of
    // this application I think its simpler (and less costly) if user metadata
    // is handled by this service
    // TODO add basic auth to this endpoint and provide auth0 with the secret
    public ResponseEntity createUser(@RequestBody Auth0User auth0User) {
        if (profileRepository.existsByUser_Username(auth0User.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            Profile newProfile = new Profile();
            newProfile.setUser(auth0User);
            return ResponseEntity.accepted().body(profileRepository.save(newProfile));
        }
    }

}
