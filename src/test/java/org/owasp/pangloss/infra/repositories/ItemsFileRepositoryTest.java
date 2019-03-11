package org.owasp.pangloss.infra.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.item.Item;
import org.owasp.pangloss.domain.item.NoItemsFoundForThisCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
public class ItemsFileRepositoryTest {

    @Autowired
    private ItemsFileRepository itemsFileRepository;

    @Test
    public void should_return_items_of_the_specified_category() {

        List<Item> expectedItems = expectedItems();

        Set<Item> items = itemsFileRepository.getAllItemsOfCategory("gameconsoles");

        assertThat(items).hasSize(2);

        assertThat(items).usingFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedItems);
    }

    @Test
    public void should_throw_NoItemsFoundForThisCategoryException() {
        assertThatThrownBy(() -> itemsFileRepository.getAllItemsOfCategory("unknown"))
                .isInstanceOf(NoItemsFoundForThisCategoryException.class)
                .hasMessage("No items found for category unknown");
    }

    private List<Item> expectedItems() {
        Item k3dx = new Item("3dx", "3DX", "Breaking your eyes in 3 dimensions, everywhere", "Kendo", new BigDecimal("199.99"));
        Item gamedude = new Item("gamedude", "Game Dude", "Maybe Old School, but has enough power to send you to the moon", "Kendo", new BigDecimal("50.00"));
        return asList(k3dx, gamedude);
    }

    @TestConfiguration
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ItemsFileRepository.class))
    static class Configuration {
    }
}