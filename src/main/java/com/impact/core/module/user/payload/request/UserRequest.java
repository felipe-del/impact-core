package com.impact.core.module.user.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "El nombre es requerido")
    String name;
    @NotBlank(message = "El email es requerido")
    String email;
}
