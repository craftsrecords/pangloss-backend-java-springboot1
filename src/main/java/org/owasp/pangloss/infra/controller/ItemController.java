package org.owasp.pangloss.infra.controller;

import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.domain.item.ItemsSelector;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ItemController {

    private Items items;

    public ItemController(Items items) {
        this.items = items;
    }

    @PostMapping(path = "items")
    public Set<Item> getItems(@RequestBody ItemsSelector itemsSelector) {
        return items.getAllItemsOfCategory(itemsSelector.getCategoryId());
    }
}
