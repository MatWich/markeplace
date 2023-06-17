package com.agroniks.marketplace.item.exceptions;

public class ItemAlreadyExists extends RuntimeException {
    public ItemAlreadyExists(String msg) {
        super(msg);
    }
}
