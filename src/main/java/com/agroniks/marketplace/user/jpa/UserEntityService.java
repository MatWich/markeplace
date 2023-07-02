package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.item.ItemService;
import com.agroniks.marketplace.item.exceptions.NoSuchItemException;
import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import com.agroniks.marketplace.user.User;
import com.agroniks.marketplace.user.exceptions.NoSuchUserException;
import com.agroniks.marketplace.user.exceptions.NotEnoughMoneyException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ItemService itemService;

    public Optional<Page<UserEntity>> findAll(PageRequest pg) {
        return Optional.of(userEntityRepository.findAll(pg));
    }

    public List<UserEntity> findByName(String name) {
        return userEntityRepository.findByName(name);
    }

    public UserEntity save(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    @Transactional
    public UserEntity updateById(UUID id, User userEntity) {
        UserEntity user = userEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("No user with id: " + id));
        user.setName(userEntity.name());
        user.setItems(userEntity.items());
        user.setMoney(userEntity.money());
        return userEntityRepository.save(user);

    }

    @Transactional
    public void deleteById(UUID id) {
        log.info(id.toString());
        userEntityRepository.deleteById(id);
    }

    private User convert(UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getItems(), userEntity.getMoney());
    }

    public UserEntity buyAsset(UUID userID, UUID itemId, Integer amount) {
        ItemEntity item = itemService.findById(itemId).orElseThrow(() -> new NoSuchItemException("No item with id: " + itemId));
        UserEntity user = userEntityRepository.findById(userID).orElseThrow(() -> new NoSuchUserException("No user with id: " + userID));
        double priceToPay = item.getWorth() * amount;

        if (user.getMoney() < priceToPay) {
            throw new NotEnoughMoneyException("Not enough money need: " + priceToPay + " have: " + user.getMoney());
        } else {
            user.setMoney(user.getMoney() - priceToPay);
            user.getItems().add(itemService.createNewItemInfoEntity(new ItemInfoEntity(UUID.randomUUID(), amount, item, user)));
            return this.updateById(userID, convert(user));
        }
    }
}
