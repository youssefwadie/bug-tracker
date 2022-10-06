package com.github.youssefwadie.bugtracker.user.exceptions;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
