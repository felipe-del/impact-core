package com.impact.core.module.supplier.mapper;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.supplier.enun.EEntityType;
import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.supplier.service.EntityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link Supplier} entities and their
 * corresponding Data Transfer Object (DTO) representations.
 * <p>
 * This class is responsible for handling conversions from entity to DTO and
 * vice versa, facilitating cleaner separation between data layers.
 */
@Component
@RequiredArgsConstructor
public class SupplierMapper {
    public final EntityTypeService entityTypeService;

    /**
     * Converts a {@link SupplierRequest} Data Transfer Object (DTO) into a {@link Supplier} entity.
     *
     * @param supplierRequest the {@link SupplierRequest} DTO containing input data
     * @return a {@link Supplier} entity populated with data from the DTO
     */
    public Supplier toEntity(SupplierRequest supplierRequest) {
        EntityType entityType = getEntityType(supplierRequest.getEntityTypeName());
        return Supplier.builder()
                .name(supplierRequest.getName())
                .phone(supplierRequest.getPhone())
                .email(supplierRequest.getEmail())
                .address(supplierRequest.getAddress())
                .entityType(entityType)
                .clientContact(supplierRequest.getClientContact())
                .idNumber(supplierRequest.getIdNumber())
                .build();
    }

    /**
     * Converts a {@link Supplier} entity into a {@link SupplierResponse}
     * Data Transfer Object (DTO).
     *
     * @param supplier the {@link Supplier} entity to convert
     * @return the corresponding {@link SupplierResponse} DTO
     */
    public SupplierResponse toDTO(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .entityTypeName(supplier.getEntityType().getTypeName().toString())
                .clientContact(supplier.getClientContact())
                .idNumber(supplier.getIdNumber())
                .build();
    }

    /**
     * Retrieves the {@link EntityType} entity based on the supplied string name.
     * This method interprets the type name and delegates to {@link EntityTypeService}
     * using the matching {@link EEntityType} enumeration.
     *
     * @param name the name of the entity type (case-insensitive)
     * @return the matching {@link EntityType} entity
     */
    public EntityType getEntityType(String name) {
        if (name.equalsIgnoreCase("legal")) {
            return entityTypeService.findByName(EEntityType.TYPE_LEGAL);
        }
        return entityTypeService.findByName(EEntityType.TYPE_PHYSICAL);
    }
}
