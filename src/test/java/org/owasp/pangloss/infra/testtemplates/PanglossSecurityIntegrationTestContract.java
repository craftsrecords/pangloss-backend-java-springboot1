package org.owasp.pangloss.infra.testtemplates;

public interface PanglossSecurityIntegrationTestContract {

    void should_return_http401_if_credentials_are_invalid();

    void should_log_the_user();
}
