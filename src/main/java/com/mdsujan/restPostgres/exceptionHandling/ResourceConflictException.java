package com.mdsujan.restPostgres.exceptionHandling;

public class ResourceConflictException extends Throwable {
    public ResourceConflictException(String message) {
        super(message);
    }
}
