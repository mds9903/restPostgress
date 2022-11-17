package com.mdsujan.restPostgres.exceptionHandling;

public class ItemConflictException extends Throwable {
    public ItemConflictException(String s) {
        super(s);
    }
}
