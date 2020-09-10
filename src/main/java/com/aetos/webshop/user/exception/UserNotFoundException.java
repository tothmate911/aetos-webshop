package com.aetos.webshop.user.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
    }

}
