package com.agroniks.marketplace.configuration;

import com.agroniks.marketplace.item.ItemService;
import com.agroniks.marketplace.item.jpa.ItemCommand;
import com.agroniks.marketplace.user.jpa.UserCommand;
import com.agroniks.marketplace.user.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserEntityService userEntityService, ItemService itemService) {
        return args -> {
            List<ItemCommand> itemList = new ArrayList<>();
            itemList.add(new ItemCommand("Item1", "Item1", 1.1));
            itemList.add(new ItemCommand("Item2", "Item2", 2.2));
            itemList.add(new ItemCommand("Item3", "Item3", 3.3));

            itemList.forEach(itemEntity -> log.info("Preloading... " + itemService.save(itemEntity)));

            log.info("Loading users to DB...");
            log.info(userEntityService.save(new UserCommand("Buyer Bob", null, 100.20)).toString());
            log.info(userEntityService.save(new UserCommand( "Seller Bob", null,0.00)).toString());
        };
    }
}
