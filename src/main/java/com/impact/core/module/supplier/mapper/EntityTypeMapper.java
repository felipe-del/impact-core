package com.impact.core.module.supplier.mapper;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.payload.response.EntityTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting {@link EntityType} entity instances
 * into their corresponding Data Transfer Object (DTO) representations.
 * <p>
 * This class is responsible for transforming entity data into a
 * simplified form suitable for exposure through APIs.
 */
@Component
@RequiredArgsConstructor
public class EntityTypeMapper {

    /**
     * Converts an {@link EntityType} entity into an {@link EntityTypeResponse}
     * Data Transfer Object (DTO).
     *
     * @param entityType the {@link EntityType} entity to convert
     * @return the corresponding {@link EntityTypeResponse} DTO
     */
    public EntityTypeResponse toDTO(EntityType entityType) {
        return EntityTypeResponse.builder()
                .id(entityType.getId())
                .typeName(entityType.getTypeName().toString())
                .build();
    }
}
