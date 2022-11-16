package com.mdsujan.restPostgres.exceptionHandling;

public class ItemIdInvalidException extends NumberFormatException{
    public ItemIdInvalidException(String message){
        super(message);
    }
}
