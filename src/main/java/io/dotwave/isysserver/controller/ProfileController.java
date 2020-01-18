package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.error.ValidationException;
import io.dotwave.isysserver.model.profile.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(@Autowired ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // we need the additional pattern matcher on this
    // mapping to enquire email addresses as usernames don't
    // get truncated after the '.'.
    @GetMapping("/{username:.+}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        if (profileRepository.existsByUsername(username)) {
            return ResponseEntity.ok(profileRepository.findByUsername(username));
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");

        if (profileRepository.existsByUsername(username)) {
            throw new ValidationException("Username already exists");
        }
        String password = body.get("password");

        // for now we can just use a random image of a cat
        Profile profile = new Profile(username, password, "https://cataas.com/cat");
        return ResponseEntity.status(HttpStatus.CREATED).body(profileRepository.save(profile));
    }

}
