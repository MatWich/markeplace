package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    @OneToMany(mappedBy = "user")
    private Set<ItemInfoEntity> items = new HashSet<>();

    private double money;

    public UserEntity(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public UserEntity(UUID id, String name, double money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }
}
