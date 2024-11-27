package com.praveen.UserService.exceptions;

import com.praveen.UserService.models.Token;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String s) {
        super(s);
    }
}
