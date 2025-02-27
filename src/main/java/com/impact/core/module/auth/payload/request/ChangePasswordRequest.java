package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "La antigua contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La antigua contraseña debe tener entre 6 y 40 caracteres")
    private String oldPassword;

    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La nueva contraseña debe tener entre 6 y 40 caracteres")
    private String newPassword;

    @NotBlank(message = "La confirmación de la nueva contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La confirmación de la nueva contraseña debe tener entre 6 y 40 caracteres")
    private String confirmNewPassword;
}
