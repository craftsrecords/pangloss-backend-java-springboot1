package org.owasp.pangloss.domain.category;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class Category {
    private UUID id;
    private String name;

    public Category(@Nonnull UUID id, @Nonnull String name) {
        this.id = requireNonNull(id, "Cannot create a category with no id");
        this.name = notBlank(name, "Cannot create a category with a blank name");
    }

    private Category() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
