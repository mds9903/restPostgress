package com.mdsujan.restPostgres.exceptionHandling;

public class PSQLException extends Throwable {
    public PSQLException(String message) {
        super(message);
    }
}
