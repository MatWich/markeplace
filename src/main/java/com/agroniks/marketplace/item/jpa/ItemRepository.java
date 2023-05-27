package com.agroniks.marketplace.item.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    String findByName(String name);
}