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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@ActiveProfiles({"insecure"})
public class RawSQLAuthenticationProviderTest extends AuthenticationProviderTestTemplate implements AuthenticationProviderTestContract {

    @Autowired
    private RawSQLAuthenticationProvider rawSQLAuthenticationProvider;

    @Before
    public void setup() {
        this.authenticationProvider = rawSQLAuthenticationProvider;
        this.authorizedUsername = "user";
        this.authorizedPassword = "pwd";
    }

    @Test
    public void should_be_vulnerable_to_SQL_injection() {
        //Given
        Authentication authentication = new TestingAuthenticationToken("qsd", "' OR '1'='1' LIMIT 1 --");
        //When
        Authentication result = authenticationProvider.authenticate(authentication);
        //Then
        assertThat(result).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getName()).isEqualTo("admin");
        assertThat(result.getCredentials()).isEqualTo("admin");
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
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RawSQLAuthenticationProvider.class}))
    static class TestAuthenticationProviderConfiguration {
    }
}