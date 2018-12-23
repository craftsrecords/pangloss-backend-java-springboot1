package org.owasp.pangloss.infra;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles({"poc"})
public class PanglossApplicationTests {

    @Value("http://localhost:${local.server.port}/profile")
    private String profileUrl;
    private TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    public void should_return_http401_if_credentials_are_invalid() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(profileUrl, JsonNode.class);
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void should_return_the_user_profile_when_successfully_logged() {
        ResponseEntity<JsonNode> response =
                restTemplate
                        .withBasicAuth("poc-user", "poc-pwd")
                        .getForEntity(profileUrl, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        JsonNode user = response.getBody();
        assertThat(user.get("name").asText()).isEqualTo("poc-user");
    }

}
