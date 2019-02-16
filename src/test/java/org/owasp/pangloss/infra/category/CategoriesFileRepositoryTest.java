package org.owasp.pangloss.infra.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CategoriesFileRepositoryTest {

    @Autowired
    private CategoriesFileRepository categoriesFileRepository;

    @Test
    public void should_read_categories_from_file() {

        Category books = new Category(UUID.fromString("0226a6e7-5f55-4d86-bbec-57a93f1dbc61"), "Books");
        Category gameConsoles = new Category(UUID.fromString("10364947-779c-4cf3-a6a7-07e7093abca2"), "Game Consoles");

        Set<Category> categories = categoriesFileRepository.allCategories();
        assertThat(categories).hasSize(2);
        assertThat(categories)
                .usingFieldByFieldElementComparator()
                .contains(books)
                .contains(gameConsoles);
    }

    @TestConfiguration
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CategoriesFileRepository.class))
    static class Configuration {
    }
}