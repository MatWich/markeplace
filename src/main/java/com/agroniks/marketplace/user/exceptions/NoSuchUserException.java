package com.agroniks.marketplace.user.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String msg) {
        super(msg);
    }
}
