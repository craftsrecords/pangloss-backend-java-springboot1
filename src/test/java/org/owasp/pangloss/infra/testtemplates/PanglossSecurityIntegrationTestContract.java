package org.owasp.pangloss.infra.testtemplates;

public interface PanglossSecurityIntegrationTestContract {

    void should_return_http401_if_credentials_are_invalid();

    void should_return_the_user_profile_when_successfully_logged();
}
