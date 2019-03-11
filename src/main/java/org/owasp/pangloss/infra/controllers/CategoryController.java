package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.domain.category.Categories;
import org.owasp.pangloss.domain.category.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CategoryController {

    private Categories categories;

    public CategoryController(Categories categories) {
        this.categories = categories;
    }

    @GetMapping(path = "api/categories")
    public Set<Category> allCategories() {
        return categories.allCategories();
    }
}
