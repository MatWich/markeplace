package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
* This class should only be doing business logic and do not perform any direct db communication
* */
@Service
public class ItemService {


    private static List<Item> items = new ArrayList<>();

    static  {
        items.add(new Item("Item1", "Item1", 1.1));
        items.add(new Item("Item2", "Item2", 2.2));
        items.add(new Item("Item3", "Item3", 3.3));
    }

    public Optional<List<Item>> findAll() {
        return Optional.of(items);
    }

    /*
    * Take one ItemEntity and converts it into Item
    * */
    private Item convertEntityToRecord(ItemEntity itemEntity) {
        // for now, it will be sufficient
        return new Item(itemEntity.getName(), itemEntity.getDescription(), itemEntity.getPrice());
    }

    private List<Item> convertEntityToRecord(List<ItemEntity> itemEntities) {
        return itemEntities.stream().map(
                this::convertEntityToRecord).collect(Collectors.toList());
    }
}
