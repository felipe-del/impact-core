package com.impact.core.module.locationNumber.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeRequest {
    @NotBlank(message = "El tipo nombre del tipo de ubicación es requerido")
    String typeName;
}
