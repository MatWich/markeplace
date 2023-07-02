package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemCommand;
import com.agroniks.marketplace.item.jpa.ItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * This class should only perform business logic and do not perform any db connection
 */

@RestController
@RequestMapping("/api/v1/item")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping("")
    public ResponseEntity<Iterable<ItemEntity>> findAllItems(Pageable pageable) {
        Page<ItemEntity> page = itemService.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "worth"))
                ));
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemEntity> findById(@PathVariable UUID id) {
        Optional<ItemEntity> itemEntityOptional = itemService.findById(id);
        return itemEntityOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("below/{worth}")
    public ResponseEntity<List<ItemEntity>> getAllItemsBelowGivenPrice(@PathVariable double worth) {
        return ResponseEntity.of(Optional.ofNullable(itemService.getAllItemsBelowGivenPrice(worth)));
    }

    @GetMapping("over/{worth}")
    public ResponseEntity<List<ItemEntity>> getAllItemsOverGivenPrice(@PathVariable double worth) {
        return ResponseEntity.of(Optional.ofNullable(itemService.getAllItemsOverGivenPrice(worth)));
    }

    @PostMapping("")
    public ResponseEntity<Void> addNewItem(@RequestBody ItemCommand itemCommand) {
        ItemEntity itemEntity = itemService.addNewItem(itemCommand);

        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/api/persistence/items/{id}")
                .buildAndExpand(itemEntity.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateById(@PathVariable UUID id, @RequestBody ItemCommand itemCommand) {
        itemService.updateById(id, itemCommand);
        return ResponseEntity.noContent().build();
    }



}