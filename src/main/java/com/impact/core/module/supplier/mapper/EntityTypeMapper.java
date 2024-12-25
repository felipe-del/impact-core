package com.impact.core.module.supplier.mapper;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.payload.response.EntityTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityTypeMapper {
    public EntityTypeResponse toDTO(EntityType entityType) {
        return EntityTypeResponse.builder()
                .id(entityType.getId())
                .typeName(entityType.getTypeName().toString())
                .build();
    }
}
