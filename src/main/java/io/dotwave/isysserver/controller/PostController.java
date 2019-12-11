package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.PostRepository;
import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.post.Post;
import io.dotwave.isysserver.util.MessageBrokerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import static io.dotwave.isysserver.util.Constants.PAGE_QUERY_PARAM;
import static io.dotwave.isysserver.util.Constants.PAGE_SIZE_QUERY_PARAM;

@RestController
@RequestMapping("/posts")
@Validated
public class PostController {

    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final MessageBrokerUtil messageBrokerUtil;

    public PostController(@Autowired PostRepository postRepository,
                          @Autowired ProfileRepository profileRepository,
                          @Autowired MessageBrokerUtil messageBrokerUtil) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
        this.messageBrokerUtil = messageBrokerUtil;
    }

    @GetMapping("")
    public ResponseEntity getPosts(@RequestParam(PAGE_QUERY_PARAM) @Min(0) Integer page,
                                   @RequestParam(PAGE_SIZE_QUERY_PARAM) int size) {
        return ResponseEntity.ok(postRepository.findAllByOrderByTimestampDesc(PageRequest.of(page, size)));
    }

    // we need the additional pattern matcher on this
    // mapping to enquire email addresses as usernames don't
    // get truncated after the '.'.
    @GetMapping("/{username:.+}")
    public ResponseEntity getUserPosts(@PathVariable("username") String username,
                                       @RequestParam(PAGE_QUERY_PARAM) @Min(0) Integer page,
                                       @RequestParam(PAGE_SIZE_QUERY_PARAM) int size) {
        if (profileRepository.existsByUser_Username(username)) {
            Page<Post> postPage = postRepository.findAllByUsernameOrderByTimestampDesc(username,
                    PageRequest.of(page, size));
            if (postPage.getTotalElements() > 0) {
                return ResponseEntity.ok(postPage);
            } else return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }


    @PostMapping("")
    public ResponseEntity createPost(@RequestBody Post post) {
        if (profileRepository.existsByUser_Username(post.getUsername())) {
            post.setTimestamp(System.currentTimeMillis() / 1000L);

            Post createdPost = postRepository.save(post);
            // send post to both the "posts" topic (for all consumers)
            // and the users individual queue
            messageBrokerUtil.sendMessage("/topic/posts", createdPost);
            messageBrokerUtil.sendMessage(String.format("/queue/%s/posts", post.getUsername()), createdPost);

            return ResponseEntity.accepted().body(createdPost);
        } else return ResponseEntity.notFound().build();
    }
}
