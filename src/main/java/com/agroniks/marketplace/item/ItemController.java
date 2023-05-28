package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * This class should only perform business logic and do not perform any db connection
 */

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("below/{price}")
    public ResponseEntity<List<Item>> getAllItemsBelowGivenPrice(@PathVariable double price) {
        return ResponseEntity.of(itemService.getAllItemsBelowGivenPrice(price));
    }

    @GetMapping("over/{price}")
    public ResponseEntity<List<Item>> getAllItemsOverGivenPrice(@PathVariable double price) {
        return ResponseEntity.of(itemService.getAllItemsOverGivenPrice(price));
    }

}