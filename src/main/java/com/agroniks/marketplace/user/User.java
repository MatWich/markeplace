package com.agroniks.marketplace.user;

import com.agroniks.marketplace.item.jpa.ItemInfoEntity;

import java.util.List;


// TODO: Change money from double to Money class
public record User(String name, List<ItemInfoEntity> items, double money) {
}
