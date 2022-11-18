package com.mdsujan.restPostgres.exceptionHandling;

public class DuplicateResourceException extends Throwable {
    public DuplicateResourceException(String s) {
        super(s);
    }
}
