package com.agroniks.marketplace.user;

import com.agroniks.marketplace.configuration.LoadDatabase;
import com.agroniks.marketplace.item.jpa.*;
import com.agroniks.marketplace.user.jpa.UserEntity;
import com.agroniks.marketplace.user.jpa.UserEntityRepository;
import com.agroniks.marketplace.user.jpa.UserEntityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserBusinessTest {

    @Autowired
    UserEntityRepository userEntityRepository;

    @MockBean
    ItemEntityService itemEntityService;

    @Autowired
    ItemInfoEntityRepository itemInfoEntityRepository;

    @MockBean
    ItemRepository itemRepository;

    @Autowired
    UserEntityService userEntityService;

    // Might be unstable but for some unknown reason I cannot mock those beans. Maybe there need to be more mocked beans
    @Test
    void shouldAddNewItemToUser() {
        // given:
        UUID userUUID = UUID.randomUUID();
        UUID itemUUID = UUID.randomUUID();

        ItemEntity itemEntity = new ItemEntity(itemUUID, "TEST ITEM", "ITEM DESC", 3);
        UserEntity user = new UserEntity(userUUID, "TESTER", 10);
        UserEntity savedUser = userEntityRepository.save(user);
        userUUID = savedUser.getId();
        // when:
//        when(userEntityRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemEntityService.findById(any())).thenReturn(Optional.of(itemEntity));
//        when(itemEntityService.createNewItemInfoEntity(any())).thenReturn(new ItemInfoEntity(itemInfoUUID, 2, itemEntity, user));
        UserEntity userEntity = userEntityService.buyAsset(userUUID, itemUUID, 2);
        // expected:
        assertTrue(userEntity.getMoney() < user.getMoney());
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

