package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")

public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ItemInfoEntity> items = new ArrayList<>();

    private double money;

    private String username;

    private String password;

    private List<String> roles = new ArrayList<>();

    public UserEntity(String name, List<ItemInfoEntity> items, double money, String username, String password, List<String> roles) {
        this.name = name;
        if (items == null)
            this.items = new ArrayList<>();
        else
            this.items = items;

        this.money = money;
        this.username = username;
        this.password = password;
        if (roles == null) {
            this.roles.add("NORMAL");
        } else {
            this.roles = roles;
        }

    }

    public UserEntity(String name, double money, String login, String password) {
        this.name = name;
        this.items = new ArrayList<>();
        this.money = money;
        this.username = login;
        this.password = password;
        this.roles.add("NORMAL");


    }
}
