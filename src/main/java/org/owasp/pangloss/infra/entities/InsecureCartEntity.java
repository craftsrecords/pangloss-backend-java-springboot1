package org.owasp.pangloss.infra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static javax.persistence.FetchType.EAGER;

@Entity(name = "cart")
public class InsecureCartEntity extends CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonIgnore
    private String owner;

    @ElementCollection(fetch = EAGER)
    private List<ItemEntity> items = emptyList();

    @Column(length = 5000)
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

    @Override
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
