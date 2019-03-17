package org.owasp.pangloss.infra.security.authentication.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UnauthorizedAuthenticationFailureHandlerTest {
    private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    public void should_set_the_httpstatus_to_401() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationFailureHandler authenticationFailureHandler = new UnauthorizedAuthenticationFailureHandler();

        authenticationFailureHandler.onAuthenticationFailure(new MockHttpServletRequest(), response, new BadCredentialsException(""));

        int expectedHttpStatus = 401;
        assertThat(response.getStatus()).isEqualTo(expectedHttpStatus);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);

        JsonNode content = parse(response.getContentAsByteArray());
        assertThat(content.get("timestamp").asLong()).isNotNegative();
        assertThat(content.get("status").asInt()).isEqualTo(expectedHttpStatus);
        assertThat(content.get("error").asText()).isEqualTo("Unauthorized");
        assertThat(content.get("message").asText()).isEqualTo("Authentication has failed");
        assertThat(content.get("path").asText()).isEqualTo("/login");
    }

    private JsonNode parse(byte[] content) throws IOException {
        return objectMapper.readTree(content);
    }
}