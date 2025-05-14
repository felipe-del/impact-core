package com.impact.core.module.productCategory.mapper;

import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import com.impact.core.module.productCategory.payload.response.UnitOfMeasurementResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper class that converts {@link UnitOfMeasurement} entities to {@link UnitOfMeasurementResponse} DTOs.
 */
@Component
public class UnitOfMeasurementMapper {

    /**
     * Converts a {@link UnitOfMeasurement} entity to a {@link UnitOfMeasurementResponse} Data Transfer Object (DTO).
     *
     * @param unitOfMeasurement The {@link UnitOfMeasurement} entity to be converted.
     * @return A {@link UnitOfMeasurementResponse} DTO containing the data of the provided entity.
     */
    public UnitOfMeasurementResponse toDTO(UnitOfMeasurement unitOfMeasurement) {
        return UnitOfMeasurementResponse.builder()
                .id(unitOfMeasurement.getId())
                .name(unitOfMeasurement.getName())
                .abbreviation(unitOfMeasurement.getAbbreviation())
                .build();
    }
}
