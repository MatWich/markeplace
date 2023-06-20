package com.agroniks.marketplace.user.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}
