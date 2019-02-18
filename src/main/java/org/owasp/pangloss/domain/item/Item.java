package org.owasp.pangloss.domain.item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public final class Item {
    private final String id;
    private final String name;
    private final String description;
    private final String brand;
    private final BigDecimal price;

    public Item(@Nonnull String id,
                @Nonnull String name,
                @Nonnull String description,
                @Nonnull String brand,
                @Nonnull BigDecimal price) {
        this.id = notBlank(id, "Cannot create an item with no id");
        this.name = notBlank(name, "Cannot create an item with a blank name");
        this.description = notBlank(description, "Cannot create an item with a blank description");
        this.brand = notBlank(brand, "Cannot create an item with a blank brand");
        this.price = validatePrice(price);
    }

    private BigDecimal validatePrice(@Nonnull BigDecimal price) {
        requireNonNull(price, "Cannot create an item with no price");
        isTrue(price.signum() == 1, "Cannot create an item with a negative price");
        return price;
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

    public BigDecimal getPrice() {
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
