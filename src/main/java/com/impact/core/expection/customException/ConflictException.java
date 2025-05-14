package com.impact.core.expection.customException;

/**
 * This class is used to throw a conflict exception when a request is in conflict.
 * <p>
 * Usually status code: {@code 409}
 * </p>
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
