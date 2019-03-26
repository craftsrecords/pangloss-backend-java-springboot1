package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("mitigated")
@AutoConfigureMockMvc
@SpringBootTest
public class SaferItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"USER"})
    public void should_return_items_of_the_given_category() throws Exception {
        mockMvc.perform(post("/api/items")
                .with(csrf())
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
    @WithMockUser(authorities = {"USER"})
    public void should_throw_BadRequest_when_trying_to_list_items_for_an_unknown_category() throws Exception {
        mockMvc.perform(post("/api/items")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content("{\"categoryId\": \"unknown\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("No items found for category unknown"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void should_delete_an_existing_item_when_admin() throws Exception {
        mockMvc.perform(
                delete("/api/items/exitingvim")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("exitingvim")));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    public void should_not_be_able_to_delete_an_item_for_a_regular_user() throws Exception {
        mockMvc.perform(
                delete("/api/items/exitingvim")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void should_throw_NotFound_when_the_id_is_unknown() throws Exception {
        mockMvc.perform(
                delete("/api/items/unknown")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}