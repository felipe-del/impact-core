package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for logging out a user by invalidating their JWT (JSON Web Token).
 * <p>
 * This Data Transfer Object (DTO) contains the JWT token that needs to be invalidated during the logout process.
 * It is typically submitted to an endpoint responsible for terminating the user's session.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    /**
     * The JWT token of the user to be invalidated.
     * Must not be blank.
     */
    @NotBlank(message = "El token no puede estar vacío")
    private String jwtToken;
}
