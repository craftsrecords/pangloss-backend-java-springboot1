package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.infra.configurations.JacksonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(JacksonConfig.class)
@WebMvcTest(
        value = ItemController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Items.class))
@WithMockUser(username = "poc-user")
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_items_of_the_given_category() throws Exception {
        mockMvc.perform(post("/items")
                .contentType(APPLICATION_JSON)
                .content("{ \"categoryId\": \"books\"}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "      \"id\": \"arbitraryforecasts\",\n" +
                        "      \"name\": \"Making Arbitrary Forecasts\",\n" +
                        "      \"description\": \"Saying something so people feel better about giving you money\",\n" +
                        "      \"brand\": \"O RLY?\",\n" +
                        "      \"price\": \"18.50\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"exitingvim\",\n" +
                        "      \"name\": \"Exiting Vim Eventually\",\n" +
                        "      \"description\": \"Just memorize these fourteen contextually dependant instructions\",\n" +
                        "      \"brand\": \"O RLY?\",\n" +
                        "      \"price\": \"15.50\"\n" +
                        "    }\n" +
                        "  ]"));
    }

    @Test
    public void should_throw_BadRequest_when_trying_to_list_items_for_an_unknown_category() throws Exception {
        mockMvc.perform(post("/items")
                .contentType(APPLICATION_JSON)
                .content("{\"categoryId\": \"unknown\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("No items found for category unknown"));
    }
}