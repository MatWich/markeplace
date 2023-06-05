package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

//    private Map<ItemEntity, Integer> items;

    private double money;


}
