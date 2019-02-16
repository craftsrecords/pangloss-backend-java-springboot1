package org.owasp.pangloss.infra.controller;

import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.domain.item.ItemsSelector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ItemController {

    private Items items;

    public ItemController(Items items) {
        this.items = items;
    }

    @GetMapping(path = "items")
    public Set<Item> getItems(@RequestBody ItemsSelector itemsSelector) {
        System.out.println(itemsSelector.getCategoryId());
        return items.getAllItemsOfCategory(itemsSelector.getCategoryId());
    }
}
