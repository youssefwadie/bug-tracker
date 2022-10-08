package com.github.youssefwadie.bugtracker.util;

import lombok.Getter;


@Getter
public final class PropertyValidationResult {
    private final String propertyName;
    private final String message;

    public static final PropertyValidationResult VALID_VALIDATION_RESULT = new PropertyValidationResult();

    public PropertyValidationResult(String propertyName, String message) {
        if (propertyName == null || propertyName.isBlank()) {
            throw new IllegalArgumentException("propertyName must not be blank");
        }

        if (message == null || propertyName.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
        this.propertyName = propertyName;
        this.message = message;
    }

    private PropertyValidationResult() {
        this.propertyName = "";
        this.message = "";
    }
}
