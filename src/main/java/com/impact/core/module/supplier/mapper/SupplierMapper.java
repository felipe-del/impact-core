package com.impact.core.module.supplier.mapper;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.supplier.enun.EEntityType;
import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.supplier.service.EntityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierMapper {
    public final EntityTypeService entityTypeService;

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

    public SupplierResponse toDTO(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .entityTypeName(supplier.getEntityType().getTypeName().name())
                .clientContact(supplier.getClientContact())
                .idNumber(supplier.getIdNumber())
                .build();
    }

    public EntityType getEntityType(String name) {
        if (name.equalsIgnoreCase("legal")) {
            return entityTypeService.findByName(EEntityType.TYPE_LEGAL);
        }
        return entityTypeService.findByName(EEntityType.TYPE_PHYSICAL);
    }
}
