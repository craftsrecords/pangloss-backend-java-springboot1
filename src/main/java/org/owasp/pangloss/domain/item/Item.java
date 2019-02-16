package org.owasp.pangloss.domain.item;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class Item {
    private final String id;
    private final String name;
    private final String description;
    private final String brand;
    private final String price;

    public Item(String id, String name, String description, String brand, String price) {
        this.id = notBlank(id, "Cannot create an item with no id");
        this.name = notBlank(name, "Cannot create an item with a blank name");
        this.description = notBlank(description, "Cannot create an item with a blank description");
        this.brand = notBlank(brand, "Cannot create an item with a blank brand");
        this.price = notBlank(price, "Cannot create an item with no price");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
