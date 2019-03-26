package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.domain.item.ItemsSelector;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/items")
@Profile({"poc", "insecure"})
public class InsecureItemController {

    private Items items;

    public InsecureItemController(Items items) {
        this.items = items;
    }

    @PostMapping
    public Set<Item> getItems(@RequestBody ItemsSelector itemsSelector) {
        return items.getAllItemsOfCategory(itemsSelector.getCategoryId());
    }

    @DeleteMapping("/{id}")
    public Item delete(@PathVariable String id) {
        return items.delete(id);
    }
}
