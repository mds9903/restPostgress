package com.mdsujan.restPostgres.exceptionHandling;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
