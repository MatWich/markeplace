package com.agroniks.marketplace.item;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
