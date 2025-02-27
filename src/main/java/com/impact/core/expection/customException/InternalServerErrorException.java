package com.impact.core.expection.customException;

/**
 * This class is used to throw an exception when a request is internal server error.
 * Usually status code: 500
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
