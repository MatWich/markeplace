package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
* This class should only be doing business logic and do not perform any direct db communication
* */
@Service
public class ItemService {

    @Autowired
    private ItemEntityService itemEntityService;


    /*
    * Take one ItemEntity and converts it into Item
    * */
    private Item convert(ItemEntity itemEntity) {
        // for now, it will be sufficient
        return new Item(itemEntity.getName(), itemEntity.getDescription(), itemEntity.getValue());
    }

    private List<Item> convert(List<ItemEntity> itemEntities) {
        return itemEntities.stream().map(
                this::convert).collect(Collectors.toList());
    }

    public Optional<List<Item>> getAllItemsBelowGivenPrice(double price) {
        List<ItemEntity> itemsEntitiesBelowGivenPrice = itemEntityService.findAll().get()
                .stream()
                .filter(itemEntity -> itemEntity.getValue() <= price)
                .sorted(Comparator.comparingDouble(ItemEntity::getValue))
                .collect(Collectors.toList());
        return Optional.of(convert(itemsEntitiesBelowGivenPrice));
    }

    public Optional<List<Item>> getAllItemsOverGivenPrice(double price) {
        List<ItemEntity> itemsEntitiesOverGivenPrice = itemEntityService.findAll().get()
                .stream()
                .filter(itemEntity -> itemEntity.getValue() >= price)
                .sorted(Comparator.comparingDouble(ItemEntity::getValue))
                .collect(Collectors.toList());
        return Optional.of(convert(itemsEntitiesOverGivenPrice));
    }
}
