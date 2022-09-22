package com.github.youssefwadie.bugtracker.security.exceptions;

public class InvalidAuthenticationSchemeException extends Exception {


    public InvalidAuthenticationSchemeException() {
        super();
    }

    public InvalidAuthenticationSchemeException(String message) {
        super(message);
    }
}
