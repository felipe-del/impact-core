package com.impact.core.expection.customException;

/**
 * This class is used to throw an internal server exception when a request is internal server error.
 * <p>
 *     Usually status code: {@code 500}
 * </p>
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
