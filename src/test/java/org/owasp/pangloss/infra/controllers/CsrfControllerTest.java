package org.owasp.pangloss.infra.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.infra.configurations.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.context.annotation.ComponentScan.Filter;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CsrfController.class, excludeFilters = @Filter(type = ASSIGNABLE_TYPE, value = WebConfig.class))
@WithMockUser("poc-user")
public class CsrfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_not_allowed_csrf_token_access_outside_authorized_cors() throws Exception {

        mockMvc.perform(get("/api/csrf")
                .header("Origin", "https://external"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_allowed_csrf_token_access_inside_authorized_cors() throws Exception {

        mockMvc.perform(get("/api/csrf")
                .header("Origin", "https://localhost:11759"))
                .andExpect(status().isOk());
    }
}