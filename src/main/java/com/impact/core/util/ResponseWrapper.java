package com.impact.core.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A generic response wrapper used alongside Spring's {@link org.springframework.http.ResponseEntity}.
 * <p>This class encapsulates a message and an optional data payload of type {@code T}, allowing for
 *  * consistent response formatting across the API. </>
 * @param <T>
 * The type of the response payload
 */
@Getter
@Setter
@Builder
public class ResponseWrapper<T> {
    private String message;
    private T data;

    /**
     * Constructs a new {@code ResponseWrapper} with a message and a data payload.
     * @param message
     * A human-readable message included in the response
     * @param data
     * Data the payload of the response, typically an entity or DTO
     */
    public ResponseWrapper(String message, T data) {
        this.message = message;
        this.data = data;
    }

    /**
     * Constructs a new {@code Response Wrapper} with only a message
     * @param message
     * Message that will be attached to the Response Wrapper
     * and that will be shown in the request result
     */
    public ResponseWrapper(String message) {
        this.message = message;
    }
}
