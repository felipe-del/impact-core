package com.impact.core.expection.customException;

/**
 * This class is used to throw an unauthorized exception when a request is not valid.
 * <p>
 *     Usually status code: {@code 400}
 * </p>
 */
public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
