package com.mdsujan.restPostgres.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = ItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleItemNotFoundException(ItemNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    // Note:
    // this is for when itemId passed is not a number;
    // however this will happen for every MethodArgumentTypeMismatchException
    // need to find a way to catch this exception specifically for GET/items/{itemId} endpoint
//    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public @ResponseBody ErrorResponse handleItemIdInvalidException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
//        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new ItemIdInvalidException("itemId should be a number; not a string").getMessage());
//    }

    @ExceptionHandler(value = DuplicateItemException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleDuplicateItemException(DuplicateItemException exception){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(value = ItemConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleItemConflictException(ItemConflictException exception){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
    }

    @ExceptionHandler(value = UpdateItemRequestBodyInvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleUpdateItemRequestBodyInvalidException(UpdateItemRequestBodyInvalidException exception){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}