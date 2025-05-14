package com.impact.core.module.mail.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Class representing a request to send a basic email.
 * <p>
 * This class is used for creating a basic email request, containing the recipient, subject, and message body.
 * It ensures that the email fields are validated with constraints for proper format and non-emptiness.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicMailRequest {

    /**
     * The recipient's email address.
     * <p>
     * This field is required and must be a valid email address format.
     * </p>
     */
    @NotBlank(message = "El destinatario no puede estar vacío")
    @Email(message = "El destinatario debe ser un correo electrónico válido")
    private String to;

    /**
     * The subject of the email.
     * <p>
     * This field is required and cannot be empty.
     * </p>
     */
    @NotBlank(message = "El asunto del correo no puede estar vacío")
    private String subject;

    /**
     * The message body of the email.
     * <p>
     * This field is optional. If not provided, no message body will be included in the email.
     * </p>
     */
    private String message;
}
