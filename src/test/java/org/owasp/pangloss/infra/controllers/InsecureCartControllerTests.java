package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_create_a_cart_with_security_flaws() throws Exception {
        System.setProperty("hacked", "false");

        AtomicReference<String> cartLocation = new AtomicReference<>();
        //XSS attack on the address
        mockMvc.perform(
                post("/api/carts")
                        .content("{\"address\": \"<script>alert('Hacked!')</script>\" }")
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.items", empty()))
                .andExpect(jsonPath("$.address", is("<script>alert('Hacked!')</script>")))
                .andDo(mvcResult -> cartLocation.set(mvcResult.getResponse().getHeader("Location")));

        //Testing regular patch
        mockMvc.perform(
                patch(cartLocation.get())
                        .content("[{ \"op\" : \"add\", \"path\" : \"/items/-\", \"value\" : { \"id\": \"test\", \"name\": \"my item\", \"price\": 100 } } ]")
                        .contentType("application/json-patch+json")
                        .accept(APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id", is("test")))
                .andExpect(jsonPath("$.items[0].name", is("my item")))
                .andExpect(jsonPath("$.items[0].price", is("100")));

        //Testing insecure deserialization
        mockMvc.perform(
                patch(cartLocation.get())
                        .content("[{ \"op\" : \"replace\", \"path\" : \"T(java.lang.System).setProperty(\\\"hacked\\\", \\\"true\\\").x\", \"value\" : \"nothing\" } ]")
                        .contentType("application/json-patch+json")
                        .accept(APPLICATION_JSON_UTF8));

        assertThat(Boolean.valueOf(System.getProperty("hacked"))).isTrue();
    }
}