package com.mdsujan.restPostgres.exceptionHandling;


public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
