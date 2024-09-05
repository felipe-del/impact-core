package com.impact.brain.exception.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 12:38 PM
 */
public class ErrorResponseDTO {
    private String error;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
