package org.owasp.pangloss.infra.security.authentication.providers;

import org.junit.Before;
import org.junit.Test;
import org.owasp.pangloss.infra.security.authentication.providers.testtemplates.AuthenticationProviderTestContract;
import org.owasp.pangloss.infra.security.authentication.providers.testtemplates.AuthenticationProviderTestTemplate;

public class PocAuthenticationProviderTest extends AuthenticationProviderTestTemplate implements AuthenticationProviderTestContract {

    @Before
    public void setup() {
        this.authenticationProvider = new PocAuthenticationProvider();
        this.authorizedUsername = "poc-user";
        this.authorizedPassword = "poc-pwd";
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
}