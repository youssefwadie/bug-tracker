package com.github.youssefwadie.bugtracker;

import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        final Map<String, Object> response = responseHeader(HttpStatus.BAD_REQUEST);
        response.put("missing", "\"" + ex.getParameterName() + "\" parameter");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintsViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintsViolationException(ConstraintsViolationException ex) {
        final Map<String, Object> response = responseHeader(HttpStatus.BAD_REQUEST);
        response.put("invalidData", ex.getErrors());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        final Map<String, Object> response = responseHeader(HttpStatus.BAD_REQUEST);
        final Throwable cause = ex.getCause();
        if (cause != null) {
            response.put("message", cause.getMessage());
        } else {
            response.put("message", ex.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        final Map<String, Object> response = responseHeader(HttpStatus.BAD_REQUEST);
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> responseHeader(HttpStatus httpStatus) {
        return new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", httpStatus.value());
            put("error", httpStatus.getReasonPhrase());
        }};
    }
}
