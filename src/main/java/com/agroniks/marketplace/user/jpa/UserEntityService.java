package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemEntityService;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import com.agroniks.marketplace.user.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ItemEntityService itemEntityService;

    public Optional<List<UserEntity>> findAll() {
        return Optional.of(userEntityRepository.findAll());
    }

    public List<UserEntity> findByName(String name) {
        return userEntityRepository.findByName(name);
    }

    public UserEntity save(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    @Transactional
    public void updateById(UUID id, User userEntity) {
        userEntityRepository.findById(id)
                .ifPresent(userEntity1 -> {
                    userEntity1.setItems(userEntity.items());
                    userEntity1.setName(userEntity.name());
                    userEntity1.setMoney(userEntity.money());
                    userEntityRepository.save(userEntity1);
                });
    }

    @Transactional
    public void deleteById(UUID id) {
        log.info(id.toString());
        userEntityRepository.deleteById(id);
    }

    private User convert(UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getItems(), userEntity.getMoney());
    }

    public boolean buyAsset(UUID userID, UUID itemId, Integer amount) {
        ItemEntity item = itemEntityService.findById(itemId).orElseThrow(() -> new RuntimeException("No item with id: " + itemId));
        UserEntity user = userEntityRepository.findById(userID).orElseThrow(() -> new RuntimeException("No user with id: " + userID));
        double priceToPay = item.getWorth() * amount;

        if (user.getMoney() < priceToPay) {
            return false;
        } else {
            user.setMoney(user.getMoney() - priceToPay);
            user.getItems().add(itemEntityService.createNewItemInfoEntity(new ItemInfoEntity(UUID.randomUUID(), amount, item, user)));
            this.updateById(userID, convert(user));
        }
        return true;
    }
}
