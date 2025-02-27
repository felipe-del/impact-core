package com.impact.core.module.space.payload.request;

import lombok.*;

import jakarta.validation.constraints.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequest {

    @NotBlank(message = "El nombre no debe estar vacío.")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres.")
    private String name;

    @NotNull(message = "El código del espacio es obligatorio.")
    @Positive(message = "El código del espacio debe ser un número positivo.")
    private int spaceCode;

    @NotNull(message = "El ID de la ubicación del edificio es obligatorio.")
    @Positive(message = "El ID de la ubicación del edificio debe ser un número positivo.")
    private int buildingLocationId;

    @NotNull(message = "El número máximo de personas es obligatorio.")
    @Positive(message = "El número máximo de personas debe ser un número positivo.")
    @Max(value = 100, message = "El número máximo de personas no puede exceder 100.")
    private int maxPeople;

    @NotBlank(message = "La hora de apertura es obligatoria.")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "La hora de apertura debe estar en el formato HH:mm.")
    private String openTime;

    @NotBlank(message = "La hora de cierre es obligatoria.")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "La hora de cierre debe estar en el formato HH:mm.")
    private String closeTime;

    @NotBlank(message = "El nombre del estado del espacio es obligatorio.")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres.")
    private String spaceStatusName;
}
