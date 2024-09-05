package com.impact.brain.exception.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 12:37 PM
 */
public class SuccessResponseDTO {
    private String message;

    public SuccessResponseDTO() {
    }

    public SuccessResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
