package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.infra.configurations.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.context.annotation.ComponentScan.Filter;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = InsecureItemController.class,
        includeFilters = @Filter(type = ASSIGNABLE_TYPE, value = Items.class),
        excludeFilters = @Filter(type = ASSIGNABLE_TYPE, value = WebConfig.class))
@ActiveProfiles("insecure")
@WithMockUser(username = "poc-user")
public class InsecureItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_items_of_the_given_category() throws Exception {
        mockMvc.perform(post("/api/items")
                .contentType(APPLICATION_JSON)
                .content("{ \"categoryId\": \"books\"}"))
                .andExpect(status().isOk())
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
        mockMvc.perform(post("/api/items")
                .contentType(APPLICATION_JSON)
                .content("{\"categoryId\": \"unknown\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("No items found for category unknown"));
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void should_delete_an_existing_item() throws Exception {
        mockMvc.perform(delete("/api/items/exitingvim"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("exitingvim")));
    }

    @Test
    public void should_throw_NotFound_when_the_id_is_unknown() throws Exception {
        mockMvc.perform(delete("/api/items/unknown"))
                .andExpect(status().isNotFound());
    }
}