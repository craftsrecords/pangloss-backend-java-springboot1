package org.owasp.pangloss.domain;

import javax.annotation.Nonnull;

import static org.apache.commons.lang3.Validate.notBlank;

public class User {
    private final String name;

    public User(@Nonnull String name) {
        this.name = notBlank(name, "name cannot be blank");
    }


    @Nonnull
    public String getName() {
        return name;
    }
}
