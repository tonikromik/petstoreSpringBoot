package com.petstore.petstoreRest.exception;

import org.springframework.dao.DataAccessException;

public class UserAlreadyExistException extends DataAccessException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
