package com.agroniks.marketplace.configuration;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemEntityService;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import com.agroniks.marketplace.item.jpa.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ItemEntityService itemEntityService) {
        return args -> {
            List<ItemEntity> itemList = new ArrayList<>();
            itemList.add(new ItemEntity("Item1", "Item1", 1.1));
            itemList.add(new ItemEntity("Item2", "Item2", 2.2));
            itemList.add(new ItemEntity("Item3", "Item3", 3.3));

            itemList.forEach(itemEntity -> itemEntity.setInfo(new ItemInfoEntity(itemEntity)));
            itemList.forEach(itemEntity -> log.info("Preloading... " + itemEntityService.addNewItem(itemEntity)));
        };
    }
}
