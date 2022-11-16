package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.customExceptions.ErrorResponse;
import com.mdsujan.restPostgres.customExceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = ItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleItemNotFoundException(ItemNotFoundException itemNotFoundException) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), itemNotFoundException.getMessage());
    }
}