package org.owasp.pangloss.infra.security.authentication.providers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Profile("poc")
public class PocAuthenticationProvider implements AuthenticationProvider {

    private static final String USERNAME = "poc-user";
    private static final String PASSWORD = "poc-pwd";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        Object credentials = authentication.getCredentials();

        if (areCredentialsCorrect(name, credentials)) {
            return new UsernamePasswordAuthenticationToken(name, credentials.toString(), null);
        }

        throw new BadCredentialsException("Unauthorized");
    }

    private boolean areCredentialsCorrect(String name, Object credentials) {
        return USERNAME.equals(name) && PASSWORD.equals(credentials);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
