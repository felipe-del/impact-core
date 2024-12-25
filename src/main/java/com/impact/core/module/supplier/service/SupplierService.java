package com.impact.core.module.supplier.service;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.supplier.enun.EEntityType;
import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("supplierService")
@RequiredArgsConstructor
public class SupplierService {
    public final SupplierRepository supplierRepository;
    public final EntityTypeService entityTypeService;

    public Supplier save(SupplierRequest supplierRequest) {
        EntityType entityType = this.getEntityType(supplierRequest.getEntityTypeName());
        Supplier supplier = toEntity(supplierRequest);
        return supplierRepository.save(supplier);
    }

    // MAPPER METHODS

    public Supplier toEntity(SupplierRequest supplierRequest) {
        return Supplier.builder()
                .id(supplierRequest.getId())
                .name(supplierRequest.getName())
                .phone(supplierRequest.getPhone())
                .email(supplierRequest.getEmail())
                .address(supplierRequest.getAddress())
                .entityType(this.getEntityType(supplierRequest.getEntityTypeName()))
                .clientContact(supplierRequest.getClientContact())
                .idNumber(supplierRequest.getIdNumber())
                .build();
    }

    public SupplierRequest toDTO(Supplier supplier) {
        return SupplierRequest.builder()
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

    // PRIVATE METHODS

    private EntityType getEntityType(String entityType) {
        return switch (entityType.toLowerCase()) {
            case "legal" -> entityTypeService.findByName(EEntityType.TYPE_LEGAL);
            default -> entityTypeService.findByName(EEntityType.TYPE_PHYSICAL);
        };
    }
}
