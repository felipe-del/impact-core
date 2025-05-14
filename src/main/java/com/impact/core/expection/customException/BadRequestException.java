package com.impact.core.expection.customException;

/**
 * This class is used to throw a bad request exception when a request is not valid.
 * <p>
 *     Usually status code: {@code 400}
 * </p>
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

