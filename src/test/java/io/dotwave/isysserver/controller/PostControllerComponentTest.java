package io.dotwave.isysserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dotwave.isysserver.data.PostRepository;
import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.post.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository mockPostRepository;

    @MockBean
    private ProfileRepository mockProfileRepository;

    @Test
    public void givenGetPosts_withValidParameters_200_withData() throws Exception {

        List<Post> mockPosts = new ArrayList<>() {
            {
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
                add(new Post());
            }
        };
        when(mockPostRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(mockPosts));

        this.mockMvc.perform(
                get("/posts")
                        .param("page", "1")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.numberOfElements").value("10"));
    }

    @Test
    public void givenCreatePosts_withValidPost_202_withData() throws Exception {

        String username = "TestUsername";

        when(mockProfileRepository.existsByUsername(username)).thenReturn(true);

        Post testPost = new Post();
        testPost.setContent("Some content");

        when(mockPostRepository.save(any(Post.class))).thenReturn(testPost);

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(
                post("/posts")
                        .content(mapper.writeValueAsString(testPost))
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(202)).andReturn();
    }

}
