package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.UserRepository;
import io.dotwave.isysserver.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userRepository.findByUsername(username));
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity getUserPosts(@PathVariable("username") String username) {
        return ResponseEntity.ok(userRepository.findByUsername(username).getPosts());
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else return ResponseEntity.accepted().body(userRepository.save(user));
    }

}
