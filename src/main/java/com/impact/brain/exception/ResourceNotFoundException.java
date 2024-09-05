package com.impact.brain.exception;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 12:36 PM
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * <p>
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message the detail message to be saved.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}