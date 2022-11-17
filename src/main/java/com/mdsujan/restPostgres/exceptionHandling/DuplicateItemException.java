package com.mdsujan.restPostgres.exceptionHandling;

public class DuplicateItemException extends Throwable {
    public DuplicateItemException(String s) {
        super(s);
    }
}
