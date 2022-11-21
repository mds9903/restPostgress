package com.mdsujan.restPostgres.exceptionHandling;

public class InvalidResourceIdException extends Throwable {
    public InvalidResourceIdException(String s) {
        super(s);
    }
}
