package com.impact.core.module.space.payload.request;

import lombok.*;

import jakarta.validation.constraints.*;

/**
 * Request Data Transfer Object (DTO) used for creating or updating a {@link com.impact.core.module.space.entity.Space}.
 * <p>
 * This class contains all necessary fields for space registration, including validation annotations
 * to ensure the integrity and format of the incoming data.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequest {

    /**
     * Name of the space.
     * <p>
     * Cannot be blank and must not exceed 100 characters.
     */
    @NotBlank(message = "El nombre no debe estar vacío.")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres.")
    private String name;

    /**
     * Numeric code used to uniquely identify the space.
     * <p>
     * Must be a positive integer.
     */
    @NotNull(message = "El código del espacio es obligatorio.")
    @Positive(message = "El código del espacio debe ser un número positivo.")
    private int spaceCode;

    /**
     * Identifier of the building location associated with the space.
     * <p>
     * Must be a positive integer.
     */
    @NotNull(message = "El ID de la ubicación del edificio es obligatorio.")
    @Positive(message = "El ID de la ubicación del edificio debe ser un número positivo.")
    private int buildingLocationId;

    /**
     * Maximum number of people allowed in the space.
     * <p>
     * Must be a positive number not greater than 100.
     */
    @NotNull(message = "El número máximo de personas es obligatorio.")
    @Positive(message = "El número máximo de personas debe ser un número positivo.")
    @Max(value = 100, message = "El número máximo de personas no puede exceder 100.")
    private int maxPeople;

    /**
     * Opening time of the space in {@code HH:mm} format.
     */
    @NotBlank(message = "La hora de apertura es obligatoria.")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "La hora de apertura debe estar en el formato HH:mm.")
    private String openTime;


    /**
     * Closing time of the space in {@code HH:mm} format.
     */
    @NotBlank(message = "La hora de cierre es obligatoria.")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "La hora de cierre debe estar en el formato HH:mm.")
    private String closeTime;

    /**
     * Validates that the opening time is before the closing time.
     *
     * @return {@code true} if {@code openTime} is lexicographically less than {@code closeTime},
     * or if either is {@code null}.
     */
    @AssertTrue(message = "La hora de apertura debe ser antes de la hora de cierre.")
    public boolean isValidTimeRange() {
        if (openTime == null || closeTime == null) {
            return true; // Se validará con @NotBlank
        }
        return openTime.compareTo(closeTime) < 0; // Verifica que openTime sea menor que closeTime
    }

    /**
     * Name of the space's current status.
     * <p>
     * Cannot be blank and must not exceed 50 characters.
     */
    @NotBlank(message = "El nombre del estado del espacio es obligatorio.")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres.")
    private String spaceStatusName;
}
