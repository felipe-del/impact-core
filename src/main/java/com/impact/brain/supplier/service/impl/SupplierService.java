package com.impact.brain.supplier.service.impl;

import com.impact.brain.exception.ResourceNotFoundException;
import com.impact.brain.supplier.dto.SupplierDTO;
import com.impact.brain.supplier.entity.EntityType;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.repository.EntityTypeRepository;
import com.impact.brain.supplier.repository.SupplierRepository;
import com.impact.brain.supplier.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:48 AM
 */
@Service
public class SupplierService implements ISupplierService {

    public final SupplierRepository supplierRepository;
    public final EntityTypeRepository entityTypeRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, EntityTypeRepository entityTypeRepository) {
        this.supplierRepository = supplierRepository;
        this.entityTypeRepository = entityTypeRepository;
    }

    @Override
    public List<SupplierDTO> getSuppliers() {
        Iterable<Supplier> suppliers = supplierRepository.findAll();

        // Verificar si no hay proveedores y devolver lista vacía
        if (!suppliers.iterator().hasNext()) {
            return new ArrayList<>(); // Devolver una lista vacía
        }

        // Convertir Iterable a List<SupplierDTO>
        return StreamSupport.stream(suppliers.spliterator(), false)
                .map(this::mapEntityToDto) // Mapeo de Supplier a SupplierDTO
                .collect(Collectors.toList());
    }


    public Iterable<EntityType> getEntityType() {
        return entityTypeRepository.findAll();
    }

    @Override
    public Supplier save(SupplierDTO supplierDTO) {
        return supplierRepository.save(mapDtoToSupplier(supplierDTO));
    }

    @Override
    public Supplier getById(int id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + id));
    }


    public Supplier mapDtoToSupplier(SupplierDTO dto) {
        // Obtener el EntityType utilizando el ID
        EntityType entityType = entityTypeRepository.findById(dto.getEntityTypeId())
                .orElseThrow(() -> new RuntimeException("Entity type not found"));

        // Crear y mapear el Supplier
        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());
        supplier.setIdNumber(dto.getIdNumber());
        supplier.setEntityType(entityType);  // Asignar el EntityType
        supplier.setClientContact(dto.getClientContact());

        return supplier;
    }
    public SupplierDTO mapEntityToDto(Supplier supplier) {
        // Validar que la entidad no sea nula
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }

        // Crear y mapear el SupplierDTO
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setPhone(supplier.getPhone());
        dto.setEmail(supplier.getEmail());
        dto.setAddress(supplier.getAddress());
        dto.setEntityTypeId(supplier.getEntityType() != null ? supplier.getEntityType().getId() : null); // Obtener solo el ID del EntityType
        dto.setClientContact(supplier.getClientContact());

        return dto;
    }

}
