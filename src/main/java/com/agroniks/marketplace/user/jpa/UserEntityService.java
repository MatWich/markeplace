package com.agroniks.marketplace.user.jpa;

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
//                    userEntity1.setItems(userEntity.getItems());
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

}
