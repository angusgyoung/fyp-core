package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.profile.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileRepository mockProfileRepository;

    @Test
    public void givenGetUser_thatExists_returnUser() throws Exception {
        String testUsername = "TestUser";

        when(mockProfileRepository.existsByUsername(testUsername)).thenReturn(true);

        this.mockMvc.perform(
                get("/users/" + testUsername))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void givenGetExistingUserPosts_returnPosts() throws Exception {

        Profile testProfile = new Profile();
        testProfile.setUsername("TestUser");

        this.mockMvc.perform(
                get("/users/TestUser/posts"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
