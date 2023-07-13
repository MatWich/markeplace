package com.agroniks.marketplace.configuration;

import com.agroniks.marketplace.item.ItemService;
import com.agroniks.marketplace.user.UserEntityService;
import com.agroniks.marketplace.user.jpa.UserCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/* The only reason for existence of this class is to override configuration for test suite  */

@Configuration
public class LoadDatabase {


    @Bean
    CommandLineRunner initDatabase(UserEntityService userEntityService, ItemService itemService) {
        return args -> {
            List<String> adminRoles = List.of("ADMIN", "NORMAL");
            userEntityService.save(new UserCommand("Admin", null, 100.0, "Admin", "Zaq12wsx", adminRoles));
            userEntityService.save(new UserCommand("Seller Bob", null, 0.00, "SBob", "SBob_pass", null));
        };
    }

}
