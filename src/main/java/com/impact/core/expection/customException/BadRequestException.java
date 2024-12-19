package com.impact.core.expection.customException;

/**
 * This class is used to throw an exception when a request is not valid.
 * Usually status code: 400
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

