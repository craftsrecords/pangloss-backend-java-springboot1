package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.infra.entities.InsecureCartEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@Profile({"poc", "insecure"})
@RepositoryRestResource(collectionResourceRel = "carts", path = "carts", itemResourceRel = "cart")
public interface CartController extends CrudRepository<InsecureCartEntity, UUID> {
}
