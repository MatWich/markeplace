package com.agroniks.marketplace.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * This class should only perform business logic and do not perform any db connection
 */

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("below/{value}")
    public ResponseEntity<List<Item>> getAllItemsBelowGivenPrice(@PathVariable double value) {
        return ResponseEntity.of(itemService.getAllItemsBelowGivenPrice(value));
    }

    @GetMapping("over/{value}")
    public ResponseEntity<List<Item>> getAllItemsOverGivenPrice(@PathVariable double value) {
        return ResponseEntity.of(itemService.getAllItemsOverGivenPrice(value));
    }

}