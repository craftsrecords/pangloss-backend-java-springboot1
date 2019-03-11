package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.infra.entities.CartEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts", itemResourceRel = "cart")
public interface CartController extends PagingAndSortingRepository<CartEntity, UUID> {
}
