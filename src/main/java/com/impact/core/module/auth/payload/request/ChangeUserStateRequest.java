package com.impact.core.module.auth.payload.request;

import com.impact.core.module.user.enun.EUserState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserStateRequest {
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;
    @NotBlank(message = "El estado del usuario no puede estar vacío")
    private String state;
}
