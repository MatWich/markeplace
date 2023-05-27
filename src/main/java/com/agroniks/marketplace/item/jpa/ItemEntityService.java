package com.agroniks.marketplace.item.jpa;

import com.agroniks.marketplace.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemEntityService {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<List<ItemEntity>> findAll() {
        return Optional.of(itemRepository.findAll());
    }

    public Optional<ItemEntity> findById(int id) {
        return itemRepository.findById(id);
    }

    /* There should not be more than one ItemEntity updated in the same request*/
    public ItemEntity convert(Item item) {
        return new ItemEntity(item.name(), item.description(), item.price());
    }

}
