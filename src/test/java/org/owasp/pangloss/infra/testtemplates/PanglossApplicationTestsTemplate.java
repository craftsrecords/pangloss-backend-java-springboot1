package org.owasp.pangloss.infra.testtemplates;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public abstract class PanglossApplicationTestsTemplate {

    private final String authorizedUserName;
    private final String authorizedUserPassword;
    @Value("http://localhost:${local.server.port}/profile")
    private String profileUrl;
    private TestRestTemplate restTemplate = new TestRestTemplate();


    protected PanglossApplicationTestsTemplate(String authorizedUserName, String authorizedUserPassword) {
        this.authorizedUserName = authorizedUserName;
        this.authorizedUserPassword = authorizedUserPassword;
    }


    protected void should_return_http401_if_credentials_are_invalid() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(profileUrl, JsonNode.class);
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }

    protected void should_return_the_user_profile_when_successfully_logged() {
        ResponseEntity<JsonNode> response =
                restTemplate
                        .withBasicAuth(authorizedUserName, authorizedUserPassword)
                        .getForEntity(profileUrl, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        JsonNode user = response.getBody();
        assertThat(user.get("name").asText()).isEqualTo(authorizedUserName);
    }
}
