package com.impact.core.module.locationNumber.payload.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationNumberRequest {
    @Positive(message = "El id del tipo de ubicación es requerido")
    int locationTypeId;
    @Positive(message = "El número de ubicación es requerido")
    int locationNumber;
}
