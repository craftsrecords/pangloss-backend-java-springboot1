package org.owasp.pangloss.domain.item;

import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ItemTest {
    private BigDecimal price = TEN;
    private String brand = "brand";
    private String description = "description";
    private String name = "name";
    private String id = "id";

    @Test
    public void should_create_item() {

        Item item = new Item(id, name, description, brand, price);

        assertThat(item.getId()).isEqualTo(id);
        assertThat(item.getBrand()).isEqualTo(brand);
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getDescription()).isEqualTo(description);
        assertThat(item.getPrice()).isEqualTo(price);
    }

    @Test
    public void should_not_create_a_category_with_no_id() {
        assertThatThrownBy(() -> new Item(null, name, description, brand, price))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Cannot create an item with no id");
    }

    @Test
    public void should_not_create_a_category_with_blank_id() {
        assertThatThrownBy(() -> new Item("     ", name, description, brand, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create an item with no id");
    }

    @Test
    public void should_not_create_a_category_with_blank_name() {
        assertThatThrownBy(() -> new Item(id, "     ", description, brand, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create an item with a blank name");
    }

    @Test
    public void should_not_create_a_category_with_blank_description() {
        assertThatThrownBy(() -> new Item(id, name, "     ", brand, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create an item with a blank description");
    }

    @Test
    public void should_not_create_a_category_with_blank_brand() {
        assertThatThrownBy(() -> new Item(id, name, description, "  ", price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create an item with a blank brand");
    }

    @Test
    public void should_not_create_a_category_with_no_price() {
        assertThatThrownBy(() -> new Item(id, name, description, brand, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Cannot create an item with no price");
    }

    @Test
    public void should_not_create_a_category_with_negative_price() {
        assertThatThrownBy(() -> new Item(id, name, description, brand, new BigDecimal("-2")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create an item with a negative price");
    }

    @Test
    public void should_satisfy_entity_equality() {

        Item item1 = new Item(id, name, description, brand, price);
        Item item2 = new Item(id, "newItem", description, brand, price);

        assertThat(item1).isEqualTo(item2);
        assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
    }

    @Test
    public void should_satisfy_entity_inequality() {

        Item item1 = new Item(randomUUID().toString(), name, description, brand, price);
        Item item2 = new Item(randomUUID().toString(), name, description, brand, price);

        assertThat(item1).isNotEqualTo(item2);
        assertThat(item1.hashCode()).isNotEqualTo(item2.hashCode());
    }
}