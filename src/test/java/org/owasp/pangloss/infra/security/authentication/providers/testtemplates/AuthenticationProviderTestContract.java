package org.owasp.pangloss.infra.security.authentication.providers.testtemplates;

public interface AuthenticationProviderTestContract {

    void should_authenticate_user();

    void should_throw_BadCredentialsException_for_unrecognized_credentials();

    void should_supports_UsernamePasswordAuthenticationToken();
}
