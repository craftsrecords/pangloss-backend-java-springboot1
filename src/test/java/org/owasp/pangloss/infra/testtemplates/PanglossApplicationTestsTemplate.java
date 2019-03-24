package org.owasp.pangloss.infra.testtemplates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

public abstract class PanglossApplicationTestsTemplate {

    private final String authorizedUserName;
    private final String authorizedUserPassword;
    @Autowired
    protected TestRestTemplate restTemplate;
    protected HttpHeaders headers;
    private String loginUrl = "/api/login";

    protected PanglossApplicationTestsTemplate(String authorizedUserName, String authorizedUserPassword) {
        this.authorizedUserName = authorizedUserName;
        this.authorizedUserPassword = authorizedUserPassword;
    }

    protected void should_return_http401_if_credentials_are_invalid() {
        ResponseEntity response = restTemplate.postForEntity(loginUrl, new HttpEntity<>(null, headers), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }

    protected void should_log_the_user() {

        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, loginRequest(headers), String.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    private HttpEntity<MultiValueMap<String, String>> loginRequest(HttpHeaders headers) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", authorizedUserName);
        body.add("password", authorizedUserPassword);

        return new HttpEntity<>(body, headers);
    }
}
