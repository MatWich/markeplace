package com.agroniks.marketplace.item.jpa;

import com.agroniks.marketplace.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persistence/items")
public class ItemEntityController {

    @Autowired
    private ItemEntityService itemEntityService;

    @GetMapping("")
    public ResponseEntity<List<ItemEntity>> findAllItems() {
        return ResponseEntity.of(itemEntityService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemEntity> findById(@PathVariable int id) {
        return ResponseEntity.of(itemEntityService.findById(id));
    }

    @GetMapping("{name}")
    public ResponseEntity<ItemEntity> findByName(@PathVariable String name) {
        return ResponseEntity.of(itemEntityService.findByName(name));
    }

    @PostMapping("")
    public ResponseEntity<Integer> addNewItem(@RequestBody Item item) {
        ItemEntity itemEntity = itemEntityService.addNewItem(itemEntityService.convert(item));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(itemEntity.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        itemEntityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateById(@PathVariable int id, @RequestBody Item item) {
        itemEntityService.updateById(id, itemEntityService.convert(item));
        return ResponseEntity.noContent().build();
    }
}
