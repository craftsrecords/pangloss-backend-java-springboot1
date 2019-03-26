package org.owasp.pangloss.domain.item;

public class UnknownItemIdException extends RuntimeException {
    public UnknownItemIdException(String id) {
        super(String.format("No item found with id %s", id));
    }
}
