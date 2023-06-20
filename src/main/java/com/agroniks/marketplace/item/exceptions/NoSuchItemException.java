package com.agroniks.marketplace.item.exceptions;


public class NoSuchItemException extends RuntimeException {
    public NoSuchItemException(String msg) {
        super(msg);
    }
}
