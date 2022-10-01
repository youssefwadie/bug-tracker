package com.github.youssefwadie.bugtracker.security.exceptions;

import java.util.Collections;
import java.util.Map;

public class ConstraintsViolationException extends IllegalArgumentException {

    private final Map<String, String> errors;

    public ConstraintsViolationException() {
        this(Collections.emptyMap());
    }

    public ConstraintsViolationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public ConstraintsViolationException(Map<String, String> errors, Throwable cause) {
        super(cause);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
