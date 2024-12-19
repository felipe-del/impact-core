package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50)
    @Email(message = "El email no es válido")
    private String email;

    private String role;

    private Integer state;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
    private String password;
}
