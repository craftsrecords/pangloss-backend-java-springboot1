package org.owasp.pangloss.infra.security.authentication.providers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.infra.security.authentication.providers.testtemplates.AuthenticationProviderTestContract;
import org.owasp.pangloss.infra.security.authentication.providers.testtemplates.AuthenticationProviderTestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@JdbcTest
@ActiveProfiles({"mitigated"})
public class SQLInjectionSafeAuthenticationProviderTest extends AuthenticationProviderTestTemplate implements AuthenticationProviderTestContract {

    @Autowired
    private SQLInjectionSafeAuthenticationProvider sqlInjectionSafeAuthenticationProvider;

    @Before
    public void setup() {
        this.authenticationProvider = sqlInjectionSafeAuthenticationProvider;
        this.authorizedUsername = "user";
        this.authorizedPassword = "pwd";
    }

    @Test
    public void should_not_be_vulnerable_to_SQL_injection() {
        //Given
        Authentication authentication = new TestingAuthenticationToken("qsd", "' OR '1'='1' LIMIT 1 --");
        //Then
        assertThatThrownBy(() -> authenticationProvider.authenticate(authentication))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    public void should_authenticate_user() {
        super.should_authenticate_user();
    }

    @Test
    public void should_throw_BadCredentialsException_for_unrecognized_credentials() {
        super.should_throw_BadCredentialsException_for_unrecognized_credentials();
    }

    @Test
    public void should_supports_UsernamePasswordAuthenticationToken() {
        super.should_supports_UsernamePasswordAuthenticationToken();
    }

    @TestConfiguration
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SQLInjectionSafeAuthenticationProvider.class}))
    static class TestAuthenticationProviderConfiguration {
    }
}