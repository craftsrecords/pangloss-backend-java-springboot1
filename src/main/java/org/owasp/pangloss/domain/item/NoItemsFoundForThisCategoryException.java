package org.owasp.pangloss.domain.item;

public final class NoItemsFoundForThisCategoryException extends RuntimeException {
    public NoItemsFoundForThisCategoryException(String unknownCategoryId) {
        super(String.format("No items found for category %s", unknownCategoryId));
    }
}
