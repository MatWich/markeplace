package com.agroniks.marketplace.user;

import com.agroniks.marketplace.configuration.LoadDatabase;
import com.agroniks.marketplace.item.exceptions.NoSuchItemException;
import com.agroniks.marketplace.item.jpa.*;
import com.agroniks.marketplace.user.exceptions.NoSuchUserException;
import com.agroniks.marketplace.user.exceptions.NotEnoughMoneyException;
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

    @Autowired
    UserEntityService userEntityService;

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
        when(itemEntityService.findById(any())).thenReturn(Optional.of(itemEntity));
        UserEntity userEntity = userEntityService.buyAsset(userUUID, itemUUID, 2);
        // expected:
        assertTrue(userEntity.getMoney() < user.getMoney());
        // clean up
        userEntityRepository.deleteAll();
    }

    @Test
    void shouldThrowExceptionIfUserDoNotExists() {
        // given:
        ItemEntity expensiveItem = new ItemEntity("Expensive Item", "Very expensive item", 999.99);
        // when:
        when(itemEntityService.findById(any())).thenReturn(Optional.of(expensiveItem));

        // expect:
        // TODO: update this when this situation will get its own exception
        assertThrows(NoSuchUserException.class, () -> userEntityService.buyAsset(UUID.randomUUID(), expensiveItem.getId(), 2));

        // clean up
        userEntityRepository.deleteAll();
    }

    @Test
    void shouldThrowExceptionIfUserDosentHasEnoughMoney() {
        // given:
        UserEntity userWithNoMoney = userEntityRepository.save(new UserEntity("TESTER", 0.0));
        ItemEntity tooExpensiveItem = new ItemEntity("Very expensive item", "Very expensive item", 999.99);
        // when:
        when(itemEntityService.findById(any())).thenReturn(Optional.of(tooExpensiveItem));

        // expect:
        assertThrows(NotEnoughMoneyException.class, () -> userEntityService.buyAsset(userWithNoMoney.getId(), tooExpensiveItem.getId(), 1));

        // clean up
        userEntityRepository.deleteAll();
    }

    @Test
    void shouldThrowExceptionIfItemThatUserWantToBuyDoesNotExist() {
        // given:
        UserEntity userWithNoMoney = userEntityRepository.save(new UserEntity("TESTER", 0.0));
        // when:
//        when(itemEntityService.findById(any())).thenReturn(null);
        // expect:
        assertThrows(NoSuchItemException.class, () -> userEntityService.buyAsset(userWithNoMoney.getId(), UUID.randomUUID(), 1));

        // clean up
        userEntityRepository.deleteAll();
    }
}

