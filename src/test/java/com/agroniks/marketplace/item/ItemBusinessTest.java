package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ItemBusinessTest {

    @MockBean
    ItemEntityService itemEntityService;

    @Autowired
    ItemService itemService;


    @Test
    void shouldReturnItemsOverPrice3() {
        // given:
        List<ItemEntity> items = getData();
        // when:
        when(itemEntityService.findAll()).thenReturn(Optional.of(items));

        // expected:
        Item expected = new Item("Item3", "Item3", 3.3);
        assertEquals(expected, itemService.getAllItemsOverGivenPrice(3.0).get().get(0));
    }

    private List<ItemEntity> getData() {
        List<ItemEntity> items = new ArrayList<>();
        items.add(new ItemEntity("Item1", "Item1", 1.1));
        items.add(new ItemEntity("Item2", "Item2", 2.2));
        items.add(new ItemEntity("Item3", "Item3", 3.3));
        return items;
    }
}
