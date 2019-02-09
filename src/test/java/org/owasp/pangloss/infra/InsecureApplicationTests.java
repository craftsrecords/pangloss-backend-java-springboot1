package org.owasp.pangloss.infra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.infra.testtemplates.PanglossApplicationTestsTemplate;
import org.owasp.pangloss.infra.testtemplates.PanglossSecurityIntegrationTestContract;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles({"insecure"})
public class InsecureApplicationTests extends PanglossApplicationTestsTemplate implements PanglossSecurityIntegrationTestContract {

    public InsecureApplicationTests() {
        super("user", "pwd");
    }

    @Test
    public void should_return_http401_if_credentials_are_invalid() {
        super.should_return_http401_if_credentials_are_invalid();
    }

    @Test
    public void should_log_the_user() {
        super.should_log_the_user();
    }
}
