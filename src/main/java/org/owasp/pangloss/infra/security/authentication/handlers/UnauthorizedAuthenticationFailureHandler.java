package org.owasp.pangloss.infra.security.authentication.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

public class UnauthorizedAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = json().build();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        exception.printStackTrace();
        int status = HttpStatus.UNAUTHORIZED.value();
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, Object> responseBody = responseBody(status);
        response.getOutputStream()
                .println(objectMapper.writeValueAsString(responseBody));
    }

    private Map<String, Object> responseBody(int status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", Instant.now().toEpochMilli());
        responseBody.put("status", status);
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", "Authentication has failed");
        responseBody.put("path", "/login");
        return responseBody;
    }
}
