package com.impact.brain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 12:36 PM
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles exceptions of type {@link ResponseStatusException}.
     *
     * @param ex the ResponseStatusException that was thrown.
     * @return a ResponseEntity containing the status code and reason message.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    /**
     * Handles exceptions of type {@link ResourceNotFoundException}.
     *
     * @param ex the ResourceNotFoundException that was thrown.
     * @return a ResponseEntity containing a map with the error message and HTTP 404 status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type {@link MethodArgumentNotValidException}.
     *
     * @param ex the MethodArgumentNotValidException that was thrown.
     * @return a ResponseEntity containing a map of validation errors and HTTP 400 status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions of type {@link Exception}.
     *
     * @param ex the Exception that was thrown.
     * @return a ResponseEntity containing a map with the error message and HTTP 500 status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralExceptions(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
