package com.impact.core.module.space_equipment.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEquipmentRequest {

    @NotBlank(message = "El nombre no debe estar vacío.")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres.")
    private String name;

    @NotNull(message = "El ID de la marca es obligatorio.")
    @Positive(message = "El ID de la marca debe ser un número positivo.")
    private int brandId;

    @NotNull(message = "El ID del espacio es obligatorio.")
    @Positive(message = "El ID del espacio debe ser un número positivo.")
    private int spaceId;

    @NotNull(message = "La cantidad es obligatoria.")
    @Positive(message = "La cantidad debe ser un número positivo.")
    private int quantity;
}
