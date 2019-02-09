package org.owasp.pangloss.infra.security.authentication.providers.testtemplates;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class AuthenticationProviderTestTemplate {

    protected AuthenticationProvider authenticationProvider;
    protected String authorizedUsername;
    protected String authorizedPassword;

    protected void should_authenticate_user() {
        //Given
        Authentication authentication = new TestingAuthenticationToken(authorizedUsername, authorizedPassword);
        //When
        Authentication result = authenticationProvider.authenticate(authentication);
        //Then
        assertThat(result).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getName()).isEqualTo(authorizedUsername);
        assertThat(result.getCredentials()).isEqualTo(authorizedPassword);
    }

    protected void should_throw_BadCredentialsException_for_unrecognized_credentials() {
        //Given
        Authentication authentication = new TestingAuthenticationToken("qsd", "mpiohzer");
        //Then
        assertThatThrownBy(() -> authenticationProvider.authenticate(authentication))
                .isInstanceOf(BadCredentialsException.class);
    }

    protected void should_supports_UsernamePasswordAuthenticationToken() {
        //When
        boolean supports = authenticationProvider.supports(UsernamePasswordAuthenticationToken.class);
        //Then
        assertThat(supports).isTrue();
    }
}
