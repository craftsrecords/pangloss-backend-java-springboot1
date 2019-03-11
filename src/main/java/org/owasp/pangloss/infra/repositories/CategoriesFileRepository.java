package org.owasp.pangloss.infra.repositories;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
        objectMapper = createObjectMapper();
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

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = json().build();
        objectMapper.registerModule(createDeserializerModule());
        return objectMapper;
    }

    private SimpleModule createDeserializerModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Category.class, new CategoryDeserializer());
        return module;
    }

    private class CategoryDeserializer extends JsonDeserializer<Category> {

        @Override
        public Category deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = readNode(parser);
            return new Category(node.get("id").asText(), node.get("name").asText());
        }

        private JsonNode readNode(JsonParser parser) throws IOException {
            ObjectCodec objectCodec = parser.getCodec();
            return objectCodec.readTree(parser);
        }
    }
}
