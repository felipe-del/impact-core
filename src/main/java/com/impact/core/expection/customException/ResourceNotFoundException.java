package com.impact.core.expection.customException;

/**
 * This class is used to throw a resource-not-found exception when a resource is not found.
 * <p>
 *     Usually status code: {@code 404}
 * </p>
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
