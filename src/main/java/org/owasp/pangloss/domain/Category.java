package org.owasp.pangloss.domain;

import javax.annotation.Nonnull;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class Category {
    private UUID id;
    private String name;

    public Category(@Nonnull UUID id, String name) {
        this.id = requireNonNull(id, "Cannot create a category with no id");
        this.name = notBlank(name, "Cannot create a category with a blank name");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
