package com.agroniks.marketplace.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
* This class should only perform business logic and do not perform any db connection
*/

@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("")
    public ResponseEntity<List<Item>> findAllItemsInMarketPlace() {
        return ResponseEntity.of(itemService.findAll());
    }
}
