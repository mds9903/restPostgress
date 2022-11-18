package com.mdsujan.restPostgres.exceptionHandling;

public class CreateResourceOperationNotAllowed extends Throwable {
    public CreateResourceOperationNotAllowed(String message) {
        super(message);
    }
}
