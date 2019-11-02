package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.PostRepository;
import io.dotwave.isysserver.data.UserRepository;
import io.dotwave.isysserver.model.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Date;

@RestController
@RequestMapping("/posts")
@Validated
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
    public ResponseEntity getPosts(@RequestParam(PAGE_QUERY_PARAM) @Min(1) Integer page,
                                   @RequestParam(SIZE_QUERY_PARAM) int size) {
        return ResponseEntity.ok(postRepository.findAll(PageRequest.of(page, size)));
    }

    @PostMapping("")
    public ResponseEntity createPost(@RequestBody @Valid Post post) {
        if (userRepository.existsByUsername(post.getUsername())) {
            post.setTimestamp(new Date().getTime());
            return ResponseEntity.accepted().body(postRepository.save(post));
        } else return ResponseEntity.notFound().build();
    }
}
