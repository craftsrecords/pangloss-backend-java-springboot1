package org.owasp.pangloss.infra.security.authenticationproviders;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@JdbcTest
public class RawSQLAuthenticationProviderTest {

    @Autowired
    private RawSQLAuthenticationProvider rawSQLAuthenticationProvider;

    @Test
    public void should_authenticate_user() {
        //Given
        String username = "user";
        String password = "pwd";
        Authentication authentication = new TestingAuthenticationToken(username, password);
        //When
        Authentication result = rawSQLAuthenticationProvider.authenticate(authentication);
        //Then
        assertThat(result).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getName()).isEqualTo(username);
        assertThat(result.getCredentials()).isEqualTo(password);
    }

    @Test
    public void should_throw_BadCredentialsException_for_unrecognized_credentials() {
        //Given
        Authentication authentication = new TestingAuthenticationToken("qsd", "mpiohzer");
        //Then
        assertThatThrownBy(() -> rawSQLAuthenticationProvider.authenticate(authentication))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    public void should_be_vulnerable_to_SQL_injection() {
        //Given
        Authentication authentication = new TestingAuthenticationToken("qsd", "' OR '1'='1' LIMIT 1 --");
        //When
        Authentication result = rawSQLAuthenticationProvider.authenticate(authentication);
        //Then
        assertThat(result).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getName()).isEqualTo("admin");
        assertThat(result.getCredentials()).isEqualTo("admin");
    }


    @Test
    public void should_supports_UsernamePasswordAuthenticationToken() {
        //When
        boolean supports = rawSQLAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class);
        //Then
        assertThat(supports).isTrue();
    }


    @TestConfiguration
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RawSQLAuthenticationProvider.class}))
    static class TestAuthenticationProviderConfiguration {
    }
}