package com.impact.core.expection.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) used to encapsulate error information in API responses.
 * <p>
 *     Typically returned when an exception occurs during request handling
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDataResponse {
    /**
     * The HTTP status code associated with the error (e.g., 401, 403)
     */
    private int status;

    /**
     * A short description for the error (e.g., Forbidden, Unauthorized)
     */
    private String error;

    /**
     * A detailed message describing the cause of the error
     */
    private String message;

    /**
     * The path or endpoint where the error occured.
     */
    private String path;
}
