package org.owasp.pangloss.infra.security.authenticationproviders;

import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PocAuthenticationProviderTest {

    private final PocAuthenticationProvider pocAuthenticationProvider = new PocAuthenticationProvider();


    @Test
    public void should_authenticate_poc_user() {
        //Given
        String username = "poc-user";
        String password = "poc-pwd";
        Authentication authentication = new TestingAuthenticationToken(username, password);
        //When
        Authentication result = pocAuthenticationProvider.authenticate(authentication);
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
        assertThatThrownBy(() -> pocAuthenticationProvider.authenticate(authentication))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    public void should_supports_UsernamePasswordAuthenticationToken() {
        //When
        boolean supports = pocAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class);
        //Then
        assertThat(supports).isTrue();
    }
}