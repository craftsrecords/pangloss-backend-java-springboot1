package org.owasp.pangloss.infra.security.authentication.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

public class OKAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper = json().build();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, Object> responseBody = responseBody();
        response.getOutputStream()
                .println(objectMapper.writeValueAsString(responseBody));

    }

    private Map<String, Object> responseBody() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "You have been successfully authenticated");
        return responseBody;
    }
}
