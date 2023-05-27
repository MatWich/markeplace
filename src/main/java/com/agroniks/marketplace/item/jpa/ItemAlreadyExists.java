package com.agroniks.marketplace.item.jpa;

public class ItemAlreadyExists extends RuntimeException {
    public ItemAlreadyExists(String msg) {
        super(msg);
    }
}
