package org.owasp.pangloss.domain.category;

import javax.annotation.Nonnull;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class Category {
    private final String id; //FYI: UUID is a better option, using String for the demo
    private final String name;

    public Category(@Nonnull String id, @Nonnull String name) {
        this.id = notBlank(id, "Cannot create a category with no id");
        this.name = notBlank(name, "Cannot create a category with a blank name");
    }

    public String getId() {
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
