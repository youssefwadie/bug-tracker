package com.github.youssefwadie.bugtracker.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BeanValidationUtils {
    private BeanValidationUtils() {

    }


    public static Map<String, String> aggregateValidationResults(List<PropertyValidationResult> validationResults) {
        final Map<String, String> errors = new HashMap<>();

        for (final var validationResult : validationResults) {
            if (validationResult != PropertyValidationResult.VALID_VALIDATION_RESULT) {
                errors.put(validationResult.getPropertyName(), validationResult.getMessage());
            }
        }
        return errors;
    }
}
