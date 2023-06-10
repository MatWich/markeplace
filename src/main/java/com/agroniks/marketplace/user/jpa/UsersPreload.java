package com.agroniks.marketplace.user.jpa;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Slf4j
public class UsersPreload {

    @Bean
    CommandLineRunner initializeUsers(UserEntityService userEntityService) {
        return args -> {
            log.info("Loading users to DB...");
            log.info(userEntityService.save(new UserEntity(UUID.fromString("8138d766-9afa-49cb-bb9c-3dfddcbd9078"), "Buyer Bob", 100.20)).toString());
            log.info(userEntityService.save(new UserEntity(UUID.randomUUID(), "Seller Bob", 0.00)).toString());
        };
    }
}
