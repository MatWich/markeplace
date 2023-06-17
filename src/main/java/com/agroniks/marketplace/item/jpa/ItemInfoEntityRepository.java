package com.agroniks.marketplace.item.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemInfoEntityRepository extends JpaRepository<ItemInfoEntity, UUID> {
}
