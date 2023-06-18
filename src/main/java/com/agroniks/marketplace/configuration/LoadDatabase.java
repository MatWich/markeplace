package com.agroniks.marketplace.configuration;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemEntityService;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import com.agroniks.marketplace.item.jpa.ItemRepository;
import com.agroniks.marketplace.user.jpa.UserEntity;
import com.agroniks.marketplace.user.jpa.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ItemEntityService itemEntityService, UserEntityService userEntityService) {
        return args -> {
            List<ItemEntity> itemList = new ArrayList<>();
            itemList.add(new ItemEntity("Item1", "Item1", 1.1));
            itemList.add(new ItemEntity("Item2", "Item2", 2.2));
            itemList.add(new ItemEntity("Item3", "Item3", 3.3));

            itemList.forEach(itemEntity -> log.info("Preloading... " + itemEntityService.addNewItem(itemEntity)));

            log.info("Loading users to DB...");
            log.info(userEntityService.save(new UserEntity(UUID.randomUUID(), "Buyer Bob", 100.20)).toString());
            log.info(userEntityService.save(new UserEntity(UUID.randomUUID(), "Seller Bob", 0.00)).toString());
        };
    }
}
