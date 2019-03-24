package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.category.Categories;
import org.owasp.pangloss.infra.configurations.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = CategoryController.class,
        includeFilters = @Filter(type = ASSIGNABLE_TYPE, value = Categories.class),
        excludeFilters = @Filter(type = ASSIGNABLE_TYPE, value = WebConfig.class))
@WithMockUser(username = "poc-user")
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_categories() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": \"books\",\n" +
                        "    \"name\": \"Books\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": \"gameconsoles\",\n" +
                        "    \"name\": \"Game Consoles\"\n" +
                        "  }\n" +
                        "]"));
    }
}