package com.github.youssefwadie.bugtracker.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtils {
    private ResponseUtils() {
    }

    public static ResponseEntity<Object> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(SimpleResponseBody.builder(HttpStatus.NOT_FOUND).message(message).build());
    }

    public static ResponseEntity<Object> badRequest(String message) {
        return ResponseEntity.badRequest().body(SimpleResponseBody.builder(HttpStatus.NOT_FOUND).message(message).build());
    }

    public static ResponseEntity<Object> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(SimpleResponseBody.builder(HttpStatus.FORBIDDEN).message(message).build());
    }
}
