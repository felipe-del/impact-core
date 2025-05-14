package com.impact.core.module.buildingLocation.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data transfer object (DTO) representing a request to create or update a building location.
 * <p>
 * This class is used for receiving data when creating or updating a building location, including the building ID
 * and the floor where the location is situated. It contains validation annotations to ensure that the inputs are valid.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingLocationRequest {

    /**
     * The ID of the building to which this location belongs.
     */
    @NotNull(message = "El id del edificio no puede estar vacío")
    int buildingId;

    /**
     * The floor where this location is situated within the building.
     */
    @NotBlank(message = "El piso no puede estar vacío")
    @Size(max = 50, message = "El piso no puede tener más de 50 caracteres")
    String floor;
}
