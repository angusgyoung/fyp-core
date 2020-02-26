package io.dotwave.isysserver.controller;

import io.dotwave.isysserver.data.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileRepository mockProfileRepository;

    @Test
    @WithMockUser
    public void givenGetUser_thatExists_returnUser() throws Exception {
        String testUsername = "TestUser";

        when(mockProfileRepository.existsByUsername(testUsername)).thenReturn(true);

        this.mockMvc.perform(
                get("/profile/" + testUsername))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
