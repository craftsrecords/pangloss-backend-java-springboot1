package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.infra.entities.SaferCartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mitigated")
@WithMockUser(username = "poc-user")
public class CartControllerTest {

    private static final String CART_URL = "/api/carts";

    @Autowired
    private SaferCartController cartRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_add_items() throws Exception {
        SaferCartEntity cartEntity = createCart();

        mockMvc.perform(
                patch(format("%s/%s", CART_URL, cartEntity.getId().toString()))
                        .with(csrf())
                        .content("[{ \"op\" : \"add\", \"path\" : \"/items/-\", \"value\" : { \"id\": \"test\", \"name\": \"my item\", \"price\": 100 } } ]")
                        .contentType("application/json-patch+json")
                        .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.items[0].id", is("test")))
                .andExpect(jsonPath("$.items[0].name", is("my item")))
                .andExpect(jsonPath("$.items[0].price", is("100")));
    }

    private SaferCartEntity createCart() {
        SaferCartEntity cart = new SaferCartEntity();
        cart.setId(UUID.randomUUID());
        cart.setOwner("poc-user");
        return cartRepository.save(cart);
    }
}