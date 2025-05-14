package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for initiating a password change operation.
 * <p>
 * This Data Transfer Object (DTO) contains the user's old password,
 * the desired new password, and a confirmation of the new password.
 * It is typically submitted to an authentication or user management controller.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    /**
     * The user's current password.
     * Must not be blank and must be between 6 and 40 characters.
     */
    @NotBlank(message = "La antigua contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La antigua contraseña debe tener entre 6 y 40 caracteres")
    private String oldPassword;

    /**
     * The new password the user wants to set.
     * Must not be blank and must be between 6 and 40 characters.
     */
    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La nueva contraseña debe tener entre 6 y 40 caracteres")
    private String newPassword;

    /**
     * Confirmation of the new password.
     * Must not be blank and must be between 6 and 40 characters.
     */
    @NotBlank(message = "La confirmación de la nueva contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La confirmación de la nueva contraseña debe tener entre 6 y 40 caracteres")
    private String confirmNewPassword;
}
