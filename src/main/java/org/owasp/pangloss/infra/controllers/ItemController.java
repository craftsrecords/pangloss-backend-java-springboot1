package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.domain.item.ItemsSelector;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/items")
public class ItemController {

    private Items items;

    public ItemController(Items items) {
        this.items = items;
    }

    @PostMapping
    public Set<Item> getItems(@RequestBody ItemsSelector itemsSelector) {
        return items.getAllItemsOfCategory(itemsSelector.getCategoryId());
    }

    @DeleteMapping("/{id}") //Fixme: remove it
    @PreAuthorize("hasAuthority('ADMIN')")
    public Item delete(@PathVariable String id) {
        return items.delete(id);
    }
}
