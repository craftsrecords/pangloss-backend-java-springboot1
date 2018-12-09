package org.owasp.pangloss.domain;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class User {
    private final String name;

    public User(@Nonnull String name) {
        this.name = requireNonNull(name, "name cannot be null");
    }


    @Nonnull
    public String getName() {
        return name;
    }
}
