package org.owasp.pangloss.infra.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTests {

    private static final String USERNAME = "poc-user";
    private static final String PROFILE = "/profile";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_poc_user() throws Exception {
        mockMvc.perform(get(PROFILE).with(user(USERNAME).password("poc-pwd")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"" + USERNAME + "\"}"));
    }

    @Test
    public void should_return_401() throws Exception {
        mockMvc.perform(get(PROFILE))
                .andExpect(status().isUnauthorized());
    }
}