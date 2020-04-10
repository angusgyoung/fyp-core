package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.profile.Profile;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(@Autowired ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // we need the additional pattern matcher on this
    // mapping to enquire email addresses as usernames don't
    // get truncated after the '.'.
    @GetMapping("")
    public ResponseEntity<Profile> getUser(@RequestParam(value = "username") String username) {
        if (profileRepository.existsByUsername(username)) {
            return ResponseEntity.ok(profileRepository.findByUsername(username));
        } else return ResponseEntity.notFound().build();
    }
}
