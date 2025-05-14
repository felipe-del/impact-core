package com.impact.core.module.locationNumber.payload.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Represents the request payload for creating or updating a location number.
 * <p>
 * This class is used to capture the necessary data for a location number, which includes the
 * ID of the location type and the location number itself.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationNumberRequest {

    /**
     * The ID of the location type associated with this location number.
     */
    @Positive(message = "El id del tipo de ubicación es requerido")
    int locationTypeId;

    /**
     * The location number.
     */
    @Positive(message = "El número de ubicación es requerido")
    int locationNumber;
}
