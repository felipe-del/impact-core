package com.impact.core.module.mail.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicMailRequest {
    @NotBlank(message = "El destinatario no puede estar vacío")
    @Email(message = "El destinatario debe ser un correo electrónico válido")
    private String to;

    @NotBlank(message = "El asunto no puede estar vacío")
    private String subject;

    private String message;
}
