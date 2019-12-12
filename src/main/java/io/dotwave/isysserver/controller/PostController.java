package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.PostRepository;
import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.error.ValidationException;
import io.dotwave.isysserver.model.post.Post;
import io.dotwave.isysserver.security.jwt.JwtTokenUtil;
import io.dotwave.isysserver.util.MessageBrokerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static io.dotwave.isysserver.util.Constants.PAGE_QUERY_PARAM;
import static io.dotwave.isysserver.util.Constants.PAGE_SIZE_QUERY_PARAM;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RestController
@RequestMapping("/posts")
@Validated
@Slf4j
public class PostController {

    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final MessageBrokerUtil messageBrokerUtil;
    private final JwtTokenUtil jwtTokenUtil;

    public PostController(@Autowired PostRepository postRepository,
                          @Autowired ProfileRepository profileRepository,
                          @Autowired MessageBrokerUtil messageBrokerUtil,
                          @Autowired JwtTokenUtil jwtTokenUtil) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
        this.messageBrokerUtil = messageBrokerUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("")
    public ResponseEntity getUserPosts(@RequestParam(value = "username") Optional<String> username,
                                       @RequestParam(value = PAGE_QUERY_PARAM) Optional<Integer> page,
                                       @RequestParam(PAGE_SIZE_QUERY_PARAM) Optional<Integer> size) {
        Integer _page = page.orElse(0);
        Integer _size = size.orElse(10);

        if (username.isPresent()) {
            String _username = username.get();
            if (profileRepository.existsByUsername(_username)) {
                Page<Post> postPage = postRepository.findAllByUsernameOrderByTimestampDesc(_username,
                        PageRequest.of(_page, _size));
                if (postPage.getTotalElements() > 0) {
                    return ResponseEntity.ok(postPage);
                } else return ResponseEntity.noContent().build();
            } else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postRepository.findAllByOrderByTimestampDesc(PageRequest.of(_page, _size)));
    }

    @PostMapping("")
    public ResponseEntity createPost(@RequestBody Post post, HttpServletRequest request) {

        String username = jwtTokenUtil.getUsernameFromAuthorizationHeader(request.getHeader(HttpHeaders.AUTHORIZATION));

        if (profileRepository.existsByUsername(username)) {
            post.setTimestamp(System.currentTimeMillis() / 1000L);
            post.setUsername(username);
            Post createdPost = postRepository.save(post);
            // send post to both the "posts" topic (for all consumers)
            // and the users individual queue
            messageBrokerUtil.sendMessage("/topic/posts", createdPost);
            messageBrokerUtil.sendMessage(String.format("/queue/%s/posts", username), createdPost);
            return ResponseEntity.accepted().body(createdPost);
        } else throw new ValidationException("User does not exist");
    }
}
