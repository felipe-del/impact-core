package com.impact.brain.auth.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:02 PM
 */
public class VerifyRequest {
    private String code;

    // Constructor, getters y setters
    public VerifyRequest() {}

    public VerifyRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
