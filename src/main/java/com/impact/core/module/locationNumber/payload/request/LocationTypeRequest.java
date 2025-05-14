package com.impact.core.module.locationNumber.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents the request payload for creating or updating a location type.
 * <p>
 * This class is used to capture the necessary data for a location type
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeRequest {

    /**
     * The name of the location type.
     */
    @NotBlank(message = "El tipo nombre del tipo de ubicación es requerido")
    String typeName;
}
