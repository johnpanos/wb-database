package com.team3256.database.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DatabaseNotFoundException extends RuntimeException {
    public DatabaseNotFoundException(String exception) {
        super(exception);
    }
}