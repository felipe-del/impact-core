package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for resetting a user's password.
 * <p>
 * This Data Transfer Object (DTO) contains the token used for verification and the new password.
 * It is typically used in an endpoint where a user can reset their password after verifying the token.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    /**
     * The verification token provided to the user for password reset.
     * Must not be blank.
     */
    @NotBlank(message = "El token verificado no puede estar vacío")
    private String token;

    /**
     * The new password for the user.
     * Must not be blank and must have a length between 6 and 40 characters.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
    private String password;
}
