package com.impact.core.module.resource_request_status.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
/**
 * Data Transfer Object (DTO) class for handling the cancellation reason
 * for a resource request.
 * <p>
 * This class is used when a resource request is being cancelled, and it captures
 * the reason for the cancellation.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelRequestDTO {

    /**
     * The reason for cancelling the resource request.
     */
    @NotBlank(message = "La razón es requerida")
    String cancelReason;
}
