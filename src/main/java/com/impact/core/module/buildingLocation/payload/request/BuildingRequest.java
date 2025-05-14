package com.impact.core.module.buildingLocation.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data transfer object (DTO) representing a request to create or update a building.
 * <p>
 * This class is used for receiving data when creating or updating a building, including its name.
 * It contains validation annotations to ensure that the input is valid.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {

    /**
     * The name of the building.
     */
    @NotBlank(message = "El nombre del edificio no puede estar vacío")
    @Size(max = 100, message = "El nombre del edificio no puede tener más de 100 caracteres")
    String name;
}
