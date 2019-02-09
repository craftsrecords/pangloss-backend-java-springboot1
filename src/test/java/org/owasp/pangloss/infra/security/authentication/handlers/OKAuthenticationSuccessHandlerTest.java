package org.owasp.pangloss.infra.security.authentication.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OKAuthenticationSuccessHandlerTest {
    private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    public void should_set_httpstatus_to_200() throws Exception {
        AuthenticationSuccessHandler authenticationSuccessHandler = new OKAuthenticationSuccessHandler();
        MockHttpServletResponse response = new MockHttpServletResponse();

        authenticationSuccessHandler.onAuthenticationSuccess(new MockHttpServletRequest(), response, null);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);

        JsonNode content = parse(response.getContentAsByteArray());
        assertThat(content.get("message").asText()).isEqualTo("You have been successfully authenticated");
    }

    private JsonNode parse(byte[] content) throws IOException {
        return objectMapper.readTree(content);
    }

}