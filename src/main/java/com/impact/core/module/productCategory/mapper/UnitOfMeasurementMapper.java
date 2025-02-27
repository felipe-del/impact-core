package com.impact.core.module.productCategory.mapper;

import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import com.impact.core.module.productCategory.payload.response.UnitOfMeasurementResponse;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasurementMapper {

    public UnitOfMeasurementResponse toDTO(UnitOfMeasurement unitOfMeasurement) {
        return UnitOfMeasurementResponse.builder()
                .id(unitOfMeasurement.getId())
                .name(unitOfMeasurement.getName())
                .abbreviation(unitOfMeasurement.getAbbreviation())
                .build();
    }
}
