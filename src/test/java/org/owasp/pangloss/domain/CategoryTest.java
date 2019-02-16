package org.owasp.pangloss.domain;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryTest {

    @Test
    public void should_create_a_category() {
        String name = "category";
        UUID id = UUID.randomUUID();

        Category category = new Category(id, name);

        assertThat(category.getId()).isEqualTo(id);
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void should_not_create_a_category_with_no_id() {
        assertThatThrownBy(() -> new Category(null, "category"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Cannot create a category with no id");
    }

    @Test
    public void should_not_create_a_category_with_a_blank_name() {
        assertThatThrownBy(() -> new Category(UUID.randomUUID(), "    "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create a category with a blank name");
    }

}