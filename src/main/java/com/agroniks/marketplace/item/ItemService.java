package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.exceptions.ItemAlreadyExists;
import com.agroniks.marketplace.item.jpa.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemInfoEntityRepository itemInfoEntityRepository;

    public List<ItemEntity> getAllItemsBelowGivenPrice(double price) {
        return itemRepository.findAll()
                .stream()
                .filter(itemEntity -> itemEntity.getWorth() <= price)
                .sorted(Comparator.comparingDouble(ItemEntity::getWorth))
                .collect(Collectors.toList());

    }

    public List<ItemEntity> getAllItemsOverGivenPrice(double price) {
        List<ItemEntity> itemsEntitiesOverGivenPrice = itemRepository.findAll()
                .stream()
                .filter(itemEntity -> itemEntity.getWorth() >= price)
                .sorted(Comparator.comparingDouble(ItemEntity::getWorth))
                .collect(Collectors.toList());
        return itemsEntitiesOverGivenPrice;
    }

    public Page<ItemEntity> findAll(PageRequest pr) {
        return itemRepository.findAll(pr);
    }

    public Optional<ItemEntity> findById(UUID id) {
        return itemRepository.findById(id);
    }

    public ItemEntity save(ItemCommand item) {
        ItemEntity byName = itemRepository.findByName(item.name());
        if (byName != null) {
            throw new ItemAlreadyExists("Item already in database");
        } else {
            ItemEntity itemEntity = new ItemEntity(item.name(), item.description(), item.worth());
            return itemRepository.save(itemEntity);
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void updateById(UUID id, ItemCommand item) {
        itemRepository.findById(id)
                .ifPresent(itemInDb -> {
                    if (item.name() != null)
                        itemInDb.setName(item.name());
                    if (item.description() != null)
                        itemInDb.setDescription(item.description());
                    if (!Double.isNaN(item.worth()))
                        itemInDb.setWorth(item.worth());
                    itemRepository.save(itemInDb);
                });
    }

    @Transactional
    public ItemInfoEntity createNewItemInfoEntity(ItemInfoEntity itemInfoEntity) {
        return itemInfoEntityRepository.save(itemInfoEntity);
    }
}
