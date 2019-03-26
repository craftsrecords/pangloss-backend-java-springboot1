package org.owasp.pangloss.domain.item;

import java.util.Set;

public interface Items {
    Set<Item> getAllItemsOfCategory(String categoryId);

    Item delete(String id);
}
