package com.impact.core.module.buildingLocation.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {
    @NotBlank(message = "El nombre del edificio no puede estar vacío")
    @Size(max = 100, message = "El nombre del edificio no puede tener más de 100 caracteres")
    String name;
}
