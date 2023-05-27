package com.agroniks.marketplace.item.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    String findByName(String name);
}