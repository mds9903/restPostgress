package com.mdsujan.restPostgres.app;

import com.mdsujan.restPostgres.excpetions.DuplicateEntryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<HttpResponse> invalidPathVariableFormat() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, "Should pass long not string!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> illegalIdInRequestBody() {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "itemId given in request body is invalid");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> requestBodyNotGiven() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, "No request body given");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<HttpResponse> duplicateEntryExcpetion() {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "default for duplicateEntryException");
    }
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        // this methods create the response in case there is an exception occurred
        return new ResponseEntity<>(new HttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()),
                httpStatus);
    }
}