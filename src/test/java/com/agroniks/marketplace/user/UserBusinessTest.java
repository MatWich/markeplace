package com.agroniks.marketplace.user;

import com.agroniks.marketplace.item.Item;
import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemRepository;
import com.agroniks.marketplace.user.jpa.UserEntity;
import com.agroniks.marketplace.user.jpa.UserEntityRepository;
import com.agroniks.marketplace.user.jpa.UserEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserBusinessTest {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserEntityService userEntityService;

    // Might be unstable but for some unknown reason I cannot mock those beans. Maybe there need to be more mocked beans
    @Test
    void shouldAddNewItemToUser() {
        // given:
        List<ItemEntity> allItems = itemRepository.findAll();
        List<UserEntity> allUsers = userEntityRepository.findAll();
        // when:
        boolean successful = userEntityService.buyAsset(getUserWithCash(allUsers).getId(), findCheapestItem(allItems).getId(), 1);
        // expected:
        assertTrue(successful);
    }


    private UserEntity getUserWithCash(List<UserEntity> allUsers) {
        return allUsers.stream()
                .max(Comparator.comparing(UserEntity::getMoney))
                .orElseThrow(() -> new RuntimeException("No users in db"));
    }

    private ItemEntity findCheapestItem(List<ItemEntity> allItems) {
        return allItems.stream()
                .min(Comparator.comparing(ItemEntity::getWorth))
                .orElseThrow(() -> new RuntimeException("No Items in db"));
    }
}

