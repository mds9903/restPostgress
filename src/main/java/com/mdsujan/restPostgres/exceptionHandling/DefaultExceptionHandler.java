package com.mdsujan.restPostgres.exceptionHandling;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

     public DefaultExceptionHandler(){
         super();
     }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorMessage> nonExistentResource(Exception exception){
        ErrorMessage errorMessageResponse = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.NOT_FOUND);
    }
}
