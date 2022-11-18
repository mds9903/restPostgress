package com.mdsujan.restPostgres.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    // Note:
    // this is for when itemId passed is not a number;
    // however this will happen for every MethodArgumentTypeMismatchException
    // need to find a way to catch this exception specifically for GET/items/{itemId} endpoint
//    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public @ResponseBody ErrorResponse handleResourceIdInvalidException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
//        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new ReourceIdInvalidException("itemId should be a number; not a string").getMessage());
//    }

    @ExceptionHandler(value = DuplicateResourceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleDuplicateResourceException(DuplicateResourceException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(value = ResourceConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleResourceConflictException(ResourceConflictException exception) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
    }

    @ExceptionHandler(value = UpdateResourceRequestBodyInvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleUpdateResourceRequestBodyInvalidException(UpdateResourceRequestBodyInvalidException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(value = CreateResourceOperationNotAllowed.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleCreateResourceOperationNotAllowed(CreateResourceOperationNotAllowed exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}