package org.owasp.pangloss.infra.entities;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class CartEventHandler {
    @HandleBeforeCreate
    public void handleCartCreate(CartEntity cartEntity) {
        if (cartEntity.getOwner() == null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            cartEntity.setOwner(username);
        }
    }
}
