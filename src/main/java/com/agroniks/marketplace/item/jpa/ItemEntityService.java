package com.agroniks.marketplace.item.jpa;

import com.agroniks.marketplace.item.Item;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ItemEntityService {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<List<ItemEntity>> findAll() {
        return Optional.of(itemRepository.findAll());
    }

    public Optional<ItemEntity> findById(UUID id) {
        return itemRepository.findById(id);
    }

    /* There should not be more than one ItemEntity updated in the same request*/
    public ItemEntity convert(Item item) {
        return new ItemEntity(item.name(), item.description(), item.worth());
    }

    public Optional<ItemEntity> findByName(String name) {
        return Optional.of(itemRepository.findByName(name));
    }

    public ItemEntity addNewItem(ItemEntity itemEntity) {
        ItemEntity byName = itemRepository.findByName(itemEntity.getName());
        if (byName != null) {
            throw new ItemAlreadyExists("Item already in database");
        } else {
            itemRepository.save(itemEntity);
            return itemRepository.findByName(itemEntity.getName());
        }
    }

    public void deleteById(UUID id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void updateById(UUID id, ItemEntity itemEntity) {
        log.info(itemEntity.toString());
        itemRepository.findById(id)
                .ifPresent(item -> {
            item.setName(itemEntity.getName());
            item.setDescription(itemEntity.getDescription());
            item.setWorth(itemEntity.getWorth());
            itemRepository.save(item);
        });
    }
}
