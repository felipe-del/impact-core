package com.impact.core.expection.customException;

/**
 * This class is used to throw an exception when a request is in conflict.
 * Usually status code: 409
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
