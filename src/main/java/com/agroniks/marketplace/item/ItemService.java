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


    /*
    * Take one ItemEntity and converts it into Item
    * */
    public Item convert(ItemEntity itemEntity) {
        // for now, it will be sufficient
        return new Item(itemEntity.getName(), itemEntity.getDescription(), itemEntity.getPrice());
    }

    public List<Item> convert(List<ItemEntity> itemEntities) {
        return itemEntities.stream().map(
                this::convert).collect(Collectors.toList());
    }
}
