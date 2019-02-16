package org.owasp.pangloss.infra.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.item.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = ItemController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Items.class))
@WithMockUser(username = "poc-user")
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_items_of_the_given_category() throws Exception {
        mockMvc.perform(get("/items").param("categoryId", "books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "      \"id\": \"arbitraryforecasts\",\n" +
                        "      \"name\": \"Making Arbitrary Forecasts\",\n" +
                        "      \"description\": \"Saying something so people feel better about giving you money\",\n" +
                        "      \"brand\": \"O RLY?\",\n" +
                        "      \"price\": \"EUR 18,50\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"exitingvim\",\n" +
                        "      \"name\": \"Exiting Vim Eventually\",\n" +
                        "      \"description\": \"Just memorize these fourteen contextually dependant instructions\",\n" +
                        "      \"brand\": \"O RLY?\",\n" +
                        "      \"price\": \"EUR 15,50\"\n" +
                        "    }\n" +
                        "  ]"));
    }

    @Test
    public void should_throw_BadRequest_when_trying_to_list_items_for_an_unknown_category() throws Exception {
        mockMvc.perform(get("/items").param("categoryId", "unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("No items found for category unknown"));
    }
}