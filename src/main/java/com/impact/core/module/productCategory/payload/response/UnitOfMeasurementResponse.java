package com.impact.core.module.productCategory.payload.response;

import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing the response for a {@link UnitOfMeasurement}.
 * Contains the fields for the {@link UnitOfMeasurement}'s ID, name, and abbreviation.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasurementResponse {

    /**
     * The unique identifier of the unit of measurement.
     */
    private int id;

    /**
     * The name of the unit of measurement.
     */
    private String name;

    /**
     * The abbreviation of the unit of measurement.
     */
    private String abbreviation;
}
