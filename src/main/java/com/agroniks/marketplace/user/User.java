package com.agroniks.marketplace.user;

import com.agroniks.marketplace.item.Item;
import java.util.Map;
// TODO: Change money from double to Money class
public record User(String name, /*Map<Item, Integer> items,*/ double money) {
}
