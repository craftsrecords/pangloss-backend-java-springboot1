package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.infra.entities.SaferCartEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@Profile("mitigated")
@RepositoryRestResource(collectionResourceRel = "carts", path = "carts", itemResourceRel = "cart")
public interface SaferCartController extends CrudRepository<SaferCartEntity, UUID> {
}