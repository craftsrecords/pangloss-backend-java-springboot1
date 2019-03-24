package org.owasp.pangloss.infra.security.authentication.providers;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MitigatedWithoutHashedPasswords implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        return environment.acceptsProfiles("mitigated") && environment.acceptsProfiles("!hashedPasswords");
    }
}
