package com.impact.brain.auth.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:02 PM
 */
public class ChangePasswordRequest {

    private String code;
    private String password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
