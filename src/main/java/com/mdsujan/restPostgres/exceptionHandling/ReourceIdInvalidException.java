package com.mdsujan.restPostgres.exceptionHandling;

public class ReourceIdInvalidException extends NumberFormatException{
    public ReourceIdInvalidException(String message){
        super(message);
    }
}
