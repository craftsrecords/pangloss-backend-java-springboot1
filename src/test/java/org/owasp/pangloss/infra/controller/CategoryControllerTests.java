package org.owasp.pangloss.infra.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.category.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = CategoryController.class,
        includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = Categories.class))
@WithMockUser(username = "poc-user")
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_categories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": \"0226a6e7-5f55-4d86-bbec-57a93f1dbc61\",\n" +
                        "    \"name\": \"Books\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": \"10364947-779c-4cf3-a6a7-07e7093abca2\",\n" +
                        "    \"name\": \"Game Consoles\"\n" +
                        "  }\n" +
                        "]"));
    }
}