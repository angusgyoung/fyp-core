package io.dotwave.isysserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dotwave.isysserver.data.PostRepository;
import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.post.Post;
import io.dotwave.isysserver.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    //@formatter:off
    @MockBean private PostRepository mockPostRepository;
    @MockBean private ProfileRepository mockProfileRepository;
    @MockBean private JwtTokenUtil mockJwtTokenUtil;
    //@formatter:on

    @Test
    @WithMockUser
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
        when(mockPostRepository.findAllByOrderByTimestampDesc(any(PageRequest.class))).thenReturn(new PageImpl<>(mockPosts));

        this.mockMvc.perform(
                get("/posts")
                        .param("page", "1")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.numberOfElements").value("10"));
    }

    @Test
    @WithMockUser
    public void givenGetPosts_withNoParameters_200_withData() throws Exception {

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
        when(mockPostRepository.findAllByOrderByTimestampDesc(any(PageRequest.class))).thenReturn(new PageImpl<>(mockPosts));

        this.mockMvc.perform(
                get("/posts"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.numberOfElements").value("10"));
    }

    @Test
    @WithMockUser
    public void givenGetPosts_withNoParameters_withNoPosts_returnNoContent() throws Exception {
        when(mockPostRepository.findAllByOrderByTimestampDesc(any(PageRequest.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        this.mockMvc.perform(
                get("/posts"))
                .andDo(print())
                .andExpect(status().is(204));
    }

    @Test
    @WithMockUser
    public void givenGetPosts_withUserParameter_returnPosts() throws Exception {

        String username = "TestUsername";

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
        when(mockPostRepository.findAllByUsernameOrderByTimestampDesc(eq(username), any(PageRequest.class))).thenReturn(new PageImpl<>(mockPosts));
        when(mockProfileRepository.existsByUsername(username)).thenReturn(true);

        this.mockMvc.perform(
                get("/posts")
                        .param("username", username)
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.numberOfElements").value("10"));
    }

    @Test
    @WithMockUser
    public void givenGetPosts_withUserParameter_noUser_returnNotFound() throws Exception {

        String username = "TestUsername";

        when(mockProfileRepository.existsByUsername(username)).thenReturn(false);

        this.mockMvc.perform(
                get("/posts")
                        .param("username", username)
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser
    public void givenGetPosts_withUserParameter_withNoPosts_returnNoContent() throws Exception {

        String username = "TestUsername";

        when(mockPostRepository.findAllByUsernameOrderByTimestampDesc(eq(username), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(mockProfileRepository.existsByUsername(username)).thenReturn(true);

        this.mockMvc.perform(
                get("/posts")
                        .param("username", username)
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().is(204));
    }

    @Test
    @WithMockUser
    public void givenCreatePosts_withValidPost_202_withData() throws Exception {

        String username = "TestUsername";

        when(mockProfileRepository.existsByUsername(username)).thenReturn(true);
        when(mockJwtTokenUtil.getUsernameFromAuthorizationHeader(any())).thenReturn(username);

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
