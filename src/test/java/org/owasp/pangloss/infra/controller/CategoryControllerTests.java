package org.owasp.pangloss.infra.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@WithMockUser(username = "poc-user")
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_categories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk());
    }

}