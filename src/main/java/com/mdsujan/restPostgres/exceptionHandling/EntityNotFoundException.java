package com.mdsujan.restPostgres.exceptionHandling;

public class EntityNotFoundException extends Exception {
    private String message;

    public EntityNotFoundException(String message) {
        super(message);
    }


}
