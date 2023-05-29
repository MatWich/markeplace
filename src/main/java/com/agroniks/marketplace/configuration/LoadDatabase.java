package com.agroniks.marketplace.configuration;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepository) {
        return args -> {
          log.info("Preloading... " + itemRepository.save(new ItemEntity("Item1", "Item1", 1.1)));
          log.info("Preloading... " + itemRepository.save(new ItemEntity("Item2", "Item2", 2.2)));
          log.info("Preloading... " + itemRepository.save(new ItemEntity("Item3", "Item3", 3.3)));
        };
    }
}
