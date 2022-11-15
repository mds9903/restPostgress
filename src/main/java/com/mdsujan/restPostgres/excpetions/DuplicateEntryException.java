package com.mdsujan.restPostgres.excpetions;

import com.mdsujan.restPostgres.entity.Item;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "duplicate itemId")
public class DuplicateEntryException extends RuntimeException {
    private Object entity;
    public DuplicateEntryException(Item item) {
        this.entity = item;
    }
}
