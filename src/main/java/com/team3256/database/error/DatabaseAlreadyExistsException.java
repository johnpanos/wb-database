package com.team3256.database.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DatabaseAlreadyExistsException extends RuntimeException {
    public DatabaseAlreadyExistsException(String exception) {
        super(exception);
    }
}
