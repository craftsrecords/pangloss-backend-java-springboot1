package org.owasp.pangloss.infra;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.infra.testtemplates.PanglossApplicationTestsTemplate;
import org.owasp.pangloss.infra.testtemplates.PanglossSecurityIntegrationTestContract;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles({"mitigated", "hashedPasswords"})
public class SaferApplicationTests extends PanglossApplicationTestsTemplate implements PanglossSecurityIntegrationTestContract {

    public SaferApplicationTests() {
        super("user", "pwd");
    }

    @Before
    public void setup() {
        ResponseEntity<JsonNode> csrfResponse = restTemplate.getForEntity("/api/csrf", JsonNode.class);
        headers = createCsrfHeaders(csrfResponse);
    }

    @Test
    public void should_return_http401_if_credentials_are_invalid() {
        super.should_return_http401_if_credentials_are_invalid();
    }

    @Test
    public void should_log_the_user() {
        super.should_log_the_user();
    }

    private HttpHeaders createCsrfHeaders(ResponseEntity<JsonNode> csrfResponse) {
        HttpHeaders headers = new HttpHeaders();
        JsonNode body = csrfResponse.getBody();
        headers.set(body.get("headerName").asText(), body.get("token").asText());
        headers.set("Cookie", csrfResponse.getHeaders().get("Set-Cookie").get(0));
        return headers;
    }
}
