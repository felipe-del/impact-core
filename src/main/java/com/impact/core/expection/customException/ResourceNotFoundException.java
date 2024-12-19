package com.impact.core.expection.customException;

/**
 * This class is used to throw an exception when a resource is not found.
 * Usually status code: 404
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
