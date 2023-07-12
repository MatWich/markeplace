package com.agroniks.marketplace.user;

import com.agroniks.marketplace.item.ItemService;
import com.agroniks.marketplace.item.exceptions.NoSuchItemException;
import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import com.agroniks.marketplace.user.exceptions.NoSuchUserException;
import com.agroniks.marketplace.user.exceptions.NotEnoughMoneyException;
import com.agroniks.marketplace.user.jpa.UserCommand;
import com.agroniks.marketplace.user.jpa.UserEntity;
import com.agroniks.marketplace.user.jpa.UserEntityRepository;
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


    public Optional<Page<UserEntity>> findAll(PageRequest pr) {
        return Optional.of(userEntityRepository.findAll(pr));
    }

    public List<UserEntity> findALL(){
        return userEntityRepository.findAll();
    }

    public UserEntity save(UserCommand user) {
       return userEntityRepository.save(new UserEntity(user.name(), user.items(), user.money(), user.username(), user.password(), user.roles()));
    }

    @Transactional
    public UserEntity updateById(UUID id, UserCommand userCommandEntity) {
        UserEntity user = userEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("No user with id: " + id));
        user.setName(userCommandEntity.name());
        user.setItems(userCommandEntity.items());
        user.setMoney(userCommandEntity.money());
        return userEntityRepository.save(user);

    }

    @Transactional
    public void deleteById(UUID id) {
        userEntityRepository.deleteById(id);
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
            return this.updateById(userID, new UserCommand(user.getName(), user.getItems(), user.getMoney(), user.getUsername(), user.getPassword(), user.getRoles()));
        }
    }

    public Optional<UserEntity> findById(UUID id) {
        return userEntityRepository.findById(id);
    }

}
