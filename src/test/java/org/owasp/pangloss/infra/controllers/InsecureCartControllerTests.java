package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.infra.entities.InsecureCartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("insecure")
@WithMockUser(username = "poc-user")
public class InsecureCartControllerTests {

    private static final String CART_URL = "/api/carts";

    @Autowired
    private CartController cartRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_be_vulnerable_to_XSS() throws Exception {
        AtomicReference<String> cartLocation = new AtomicReference<>();

        mockMvc.perform(
                post(CART_URL)
                        .content("{\"address\": \"<script>alert('Hacked!')</script>\" }")
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.items", empty()))
                .andExpect(jsonPath("$.address", is("<script>alert('Hacked!')</script>")))
                .andDo(mvcResult -> cartLocation.set(mvcResult.getResponse().getHeader("Location")));

        String cartId = extractCartId(cartLocation);
        InsecureCartEntity cartResult = cartRepository.findOne(UUID.fromString(cartId));
        assertThat(cartResult.getOwner()).isEqualTo("poc-user");
    }

    @Test
    public void should_be_vulnerable_to_insecure_deserialization() throws Exception {
        System.setProperty("hacked", "false");
        InsecureCartEntity cartEntity = createCart();

        mockMvc.perform(
                patch(format("%s/%s", CART_URL, cartEntity.getId().toString()))
                        .content("[{ \"op\" : \"replace\", \"path\" : \"T(java.lang.System).setProperty(\\\"hacked\\\", \\\"true\\\").x\", \"value\" : \"nothing\" } ]")
                        .contentType("application/json-patch+json")
                        .accept(APPLICATION_JSON_UTF8));

        assertThat(Boolean.valueOf(System.getProperty("hacked"))).isTrue();
    }

    private InsecureCartEntity createCart() {
        return cartRepository.save(new InsecureCartEntity());
    }

    private String extractCartId(AtomicReference<String> cartLocation) {
        return cartLocation.get().substring("http://localhost/api/carts/".length());
    }
}