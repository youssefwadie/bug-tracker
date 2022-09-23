package com.github.youssefwadie.bugtracker.security.exceptions;

import java.util.Collections;
import java.util.Map;

public class ConstraintViolationException extends IllegalArgumentException {

    private final Map<String, String> errors;

    public ConstraintViolationException() {
        this(Collections.emptyMap());
    }

    public ConstraintViolationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
