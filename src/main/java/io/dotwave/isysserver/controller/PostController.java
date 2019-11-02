package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.PostRepository;
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

    public PostController(@Autowired PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("")
    public ResponseEntity getPosts(@RequestParam(PAGE_QUERY_PARAM) int page,
                                   @RequestParam(SIZE_QUERY_PARAM) int size) {
        return ResponseEntity.ok(postRepository.findAll(PageRequest.of(page, size)));
    }

    @PostMapping("")
    public ResponseEntity createPost(@RequestBody Post post) {
        return ResponseEntity.status(202).body(postRepository.save(new Post(post.getContent(),
                post.getUsername())));
    }
}
