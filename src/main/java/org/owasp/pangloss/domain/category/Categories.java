package org.owasp.pangloss.domain.category;

import java.util.Set;

@FunctionalInterface
public interface Categories {
    Set<Category> allCategories();
}
