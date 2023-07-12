package com.agroniks.marketplace.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {
    List<UserEntity> findByName(String name);
    UserEntity findUserByUsername(String username);
}
