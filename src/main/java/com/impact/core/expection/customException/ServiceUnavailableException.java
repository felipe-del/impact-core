package com.impact.core.expection.customException;

/**
 * This class is used to throw a service unavailable exception when a service is unavailable.
 * <p>
 *     Usually status code: {@code 503}
 * </p>
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
