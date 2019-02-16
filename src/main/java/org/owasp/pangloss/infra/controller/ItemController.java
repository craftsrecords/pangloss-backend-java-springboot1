package org.owasp.pangloss.infra.controller;

import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.Items;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ItemController {

    private Items items;

    public ItemController(Items items) {
        this.items = items;
    }

    @GetMapping(path = "items")
    public Set<Item> getAllItemsOfCategory(@RequestParam String categoryId) {
        return items.getAllItemsOfCategory(categoryId);
    }
}
