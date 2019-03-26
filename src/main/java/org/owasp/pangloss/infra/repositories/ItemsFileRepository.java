package org.owasp.pangloss.infra.repositories;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.domain.item.NoItemsFoundForThisCategoryException;
import org.owasp.pangloss.domain.item.UnknownItemIdException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

@Repository
public class ItemsFileRepository implements Items {

    private final ObjectMapper objectMapper;

    @Value("classpath:data/itemsByCategory.json")
    private Resource itemsByCategoryFile;
    private Map<String, Set<Item>> itemsByCategory;

    public ItemsFileRepository() {
        objectMapper = createObjectMapper();
    }

    @Override
    public Set<Item> getAllItemsOfCategory(String categoryId) {
        return ofNullable(itemsByCategory.get(categoryId))
                .orElseThrow(() -> new NoItemsFoundForThisCategoryException(categoryId));
    }

    @Override
    public Item delete(String id) {
        Item itemToDelete = retrieveItemToDelete(id);
        String categoryOfTheItem = getCategoryOfTheItem(itemToDelete);
        itemsByCategory.get(categoryOfTheItem).remove(itemToDelete);
        return itemToDelete;
    }

    private String getCategoryOfTheItem(Item itemToDelete) {
        return itemsByCategory.entrySet().stream()
                .filter(entry -> entry.getValue().contains(itemToDelete))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

    private Item retrieveItemToDelete(String id) {
        return itemsByCategory.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UnknownItemIdException(id));
    }

    @PostConstruct
    protected void readItemsByCategory() {
        try {
            itemsByCategory = objectMapper.readValue(itemsByCategoryFile.getInputStream(), new TypeReference<Map<String, Set<Item>>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = json().build();
        objectMapper.registerModule(createDeserializerModule());
        return objectMapper;
    }

    private SimpleModule createDeserializerModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, new ItemDeserializer());
        return module;
    }

    private class ItemDeserializer extends JsonDeserializer<Item> {

        @Override
        public Item deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = readNode(parser);
            return new Item(node.get("id").asText(),
                    node.get("name").asText(),
                    node.get("description").asText(),
                    node.get("brand").asText(),
                    new BigDecimal(node.get("price").asText()));
        }

        private JsonNode readNode(JsonParser parser) throws IOException {
            ObjectCodec objectCodec = parser.getCodec();
            return objectCodec.readTree(parser);
        }
    }
}
