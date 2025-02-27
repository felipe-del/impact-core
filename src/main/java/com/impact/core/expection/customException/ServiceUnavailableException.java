package com.impact.core.expection.customException;

/**
 * This class is used to throw an exception when a service is unavailable.
 * Usually status code: 503
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
