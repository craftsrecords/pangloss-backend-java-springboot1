package org.owasp.pangloss.infra.entities;

import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static javax.persistence.FetchType.EAGER;

@Entity(name = "cart")
public class SaferCartEntity extends CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ElementCollection(fetch = EAGER)
    private List<ItemEntity> items = emptyList();

    @SafeHtml
    private String address = "";

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
