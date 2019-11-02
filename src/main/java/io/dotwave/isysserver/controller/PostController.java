package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.PostRepository;
import io.dotwave.isysserver.data.UserRepository;
import io.dotwave.isysserver.model.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private static final String PAGE_QUERY_PARAM = "page";
    private static final String SIZE_QUERY_PARAM = "size";

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostController(@Autowired PostRepository postRepository,
                          @Autowired UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public ResponseEntity getPosts(@RequestParam(PAGE_QUERY_PARAM) int page,
                                   @RequestParam(SIZE_QUERY_PARAM) int size) {
        return ResponseEntity.ok(postRepository.findAll(PageRequest.of(page, size)));
    }

    @PostMapping("")
    public ResponseEntity createPost(@RequestBody Post post) {
        if (userRepository.existsByUsername(post.getUsername())) {
            return ResponseEntity.accepted().body(postRepository.save(post));
        } else return ResponseEntity.notFound().build();
    }
}
