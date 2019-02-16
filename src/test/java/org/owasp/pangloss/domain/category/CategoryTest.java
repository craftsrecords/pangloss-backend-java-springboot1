package org.owasp.pangloss.domain.category;

import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryTest {

    @Test
    public void should_create_a_category() {
        String name = "category";
        String id = randomUUID().toString();

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
    public void should_not_create_a_category_with_blank_id() {
        assertThatThrownBy(() -> new Category("      ", "category"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create a category with no id");
    }

    @Test
    public void should_not_create_a_category_with_a_blank_name() {
        assertThatThrownBy(() -> new Category("id", "    "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create a category with a blank name");
    }

    @Test
    public void should_satisfy_entity_equality() {
        //In DDD entities equality are only base on IDs

        String id = randomUUID().toString();
        Category category1 = new Category(id, "category1");
        Category category2 = new Category(id, "category2");

        assertThat(category1).isEqualTo(category2);
        assertThat(category1.hashCode()).isEqualTo(category2.hashCode());
    }

    @Test
    public void should_satisfy_entity_inequality() {

        String name = "category";
        Category category1 = new Category(randomUUID().toString(), name);
        Category category2 = new Category(randomUUID().toString(), name);

        assertThat(category1).isNotEqualTo(category2);
        assertThat(category1.hashCode()).isNotEqualTo(category2.hashCode());
    }
}