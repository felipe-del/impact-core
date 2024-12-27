package com.impact.core.module.buildingLocation.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingLocationRequest {
    @NotNull(message = "El id del edificio no puede estar vacío")
    int buildingId;
    @NotBlank(message = "El piso no puede estar vacío")
    @Size(max = 50, message = "El piso no puede tener más de 50 caracteres")
    String floor;
}
