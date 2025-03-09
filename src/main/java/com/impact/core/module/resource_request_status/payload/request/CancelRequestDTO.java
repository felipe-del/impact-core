package com.impact.core.module.resource_request_status.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelRequestDTO {
    @NotBlank(message = "La razón es requerida")
    String cancelReason;
}
