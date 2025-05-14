package com.impact.core.module.space_equipment.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data Transfer Object (DTO) for representing a request to create or update space equipment.
 * <p>
 * This class encapsulates the input data required for creating or updating space equipment, such as its name,
 * brand ID, space ID, and quantity. It is used to transfer data from the client to the server, typically via a REST API.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEquipmentRequest {

    /**
     * The name of the space equipment.
     * <p>
     * This field cannot be blank and has a maximum length of 100 characters.
     * </p>
     */
    @NotBlank(message = "El nombre no debe estar vacío.")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres.")
    private String name;

    /**
     * The ID of the brand associated with the space equipment.
     * <p>
     * This field must be a positive number and cannot be null.
     * </p>
     */
    @NotNull(message = "El ID de la marca es obligatorio.")
    @Positive(message = "El ID de la marca debe ser un número positivo.")
    private int brandId;

    /**
     * The ID of the space where the equipment is located.
     * <p>
     * This field must be a positive number and cannot be null.
     * </p>
     */
    @NotNull(message = "El ID del espacio es obligatorio.")
    @Positive(message = "El ID del espacio debe ser un número positivo.")
    private int spaceId;

    /**
     * The quantity of the space equipment.
     * <p>
     * This field must be a positive number and cannot be null.
     * </p>
     */
    @NotNull(message = "La cantidad es obligatoria.")
    @Positive(message = "La cantidad debe ser un número positivo.")
    private int quantity;
}
