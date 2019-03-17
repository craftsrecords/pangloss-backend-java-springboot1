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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_XHTML_XML;
import static org.springframework.http.MediaType.TEXT_HTML;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("insecure")
public class InsecureWebSecurityConfigTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_not_require_csrf_token() {

        ResponseEntity<String> response =
                restTemplate
                        .withBasicAuth("user", "pwd")
                        .exchange("/api/profile", GET, request(), String.class);
        List<String> cookie = response.getHeaders().get("Set-Cookie");

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(cookie).noneMatch(cookieContent -> cookieContent.contains("XSRF-TOKEN"));
    }

    @Test
    public void should_allow_cross_origin_requests() {
        HttpEntity<String> request = requestWithExternalOrigin();

        ResponseEntity<String> response =
                restTemplate
                        .withBasicAuth("user", "pwd")
                        .exchange("/api/profile", GET, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    private HttpEntity<String> requestWithExternalOrigin() {
        HttpHeaders origin = new HttpHeaders();
        origin.setOrigin("http://externalorigin");

        return new HttpEntity<>("", origin);
    }

    private HttpEntity<String> request() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(TEXT_HTML, APPLICATION_XHTML_XML));
        return new HttpEntity<>("", headers);
    }
}