package org.owasp.pangloss.infra.configurations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("mitigated")
public class SaferWebSecurityConfigTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_set_csrf_token() {

        ResponseEntity<String> response = restTemplate.getForEntity("/api/csrf", String.class);

        List<String> cookie = response.getHeaders().get("Set-Cookie");

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(cookie).anyMatch(cookieContent -> cookieContent.contains("XSRF-TOKEN"));
    }

    @Test
    public void should_not_allow_external_cross_origin_requests() {
        HttpEntity<String> request = requestWithOrigin("http://externalorigin");

        ResponseEntity<String> response =
                restTemplate
                        .withBasicAuth("user", "pwd")
                        .exchange("/api/profile", GET, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @Test
    public void should_allow_pangloss_cross_origin_requests() {
        HttpEntity<String> request = requestWithOrigin("http://localhost:11759");

        ResponseEntity<String> response =
                restTemplate
                        .withBasicAuth("user", "pwd")
                        .exchange("/api/profile", GET, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    private HttpEntity<String> requestWithOrigin(String originUrl) {
        HttpHeaders origin = new HttpHeaders();
        origin.setOrigin(originUrl);

        return new HttpEntity<>("", origin);
    }
}