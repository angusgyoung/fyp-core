package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.UserRepository;
import io.dotwave.isysserver.model.post.Post;
import io.dotwave.isysserver.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository mockUserRepository;

    @Test
    public void givenGetUser_thatExists_returnUser() throws Exception {
        String testUsername = "TestUser";

        when(mockUserRepository.existsByUsername(testUsername)).thenReturn(true);

        this.mockMvc.perform(
                get("/users/" + testUsername))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void givenGetExistingUserPosts_returnPosts() throws Exception {

        User testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setPosts(new ArrayList<>() {{
            add(new Post("Test", testUser.getUsername()));
            add(new Post("Test", testUser.getUsername()));
            add(new Post("Test", testUser.getUsername()));
            add(new Post("Test", testUser.getUsername()));
        }});
        when(mockUserRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
        when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);

        this.mockMvc.perform(
                get("/users/TestUser/posts"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
