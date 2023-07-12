package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.item.jpa.ItemInfoEntity;

import java.util.List;


// TODO: Change money from double to Money class
public record UserCommand(String name, List<ItemInfoEntity> items, double money, String username, String password, List<String> roles) {
}
