package org.owasp.pangloss.infra.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.owasp.pangloss.domain.category.Categories;
import org.owasp.pangloss.domain.category.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Set;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

@Repository
public class CategoriesFileRepository implements Categories {

    private final ObjectMapper objectMapper;

    @Value("classpath:data/categories.json")
    private Resource categoriesFile;

    public CategoriesFileRepository() {
        objectMapper = json().build();
    }

    @Override
    public Set<Category> allCategories() {
        return readCategories();
    }

    private Set<Category> readCategories() {
        Set<Category> categories;
        try {
            categories =
                    objectMapper.readValue(categoriesFile.getInputStream(), new TypeReference<Set<Category>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}
