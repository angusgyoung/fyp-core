package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.UserRepository;
import io.dotwave.isysserver.model.user.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) {
        if (userRepository.existsByUsername(username))
            return ResponseEntity.ok(userRepository.findByUsername(username));
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity getUserPosts(@PathVariable("username") String username) {
        if (userRepository.existsByUsername(username))
            return ResponseEntity.ok(userRepository.findByUsername(username).getPosts());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody @Valid User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else return ResponseEntity.accepted().body(userRepository.save(user));
    }

}
