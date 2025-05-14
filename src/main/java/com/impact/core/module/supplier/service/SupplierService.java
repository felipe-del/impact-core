package com.impact.core.module.supplier.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.supplier.mapper.SupplierMapper;
import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link Supplier} entities.
 * <p>
 * This service provides methods for creating, updating, deleting, and retrieving {@link Supplier} entities.
 */
@Service("supplierService")
@RequiredArgsConstructor
public class SupplierService {
    public final SupplierRepository supplierRepository;
    public final SupplierMapper supplierMapper;

    /**
     * Saves a new {@link Supplier} entity.
     * <p>
     * This method maps a {@link SupplierRequest} DTO to a {@link Supplier} entity, persists it in the database,
     * and returns the saved {@link Supplier} as a {@link SupplierResponse}.
     *
     * @param supplierRequest The {@link SupplierRequest} DTO containing the data for the new supplier.
     * @return The saved {@link Supplier} as a {@link SupplierResponse} DTO.
     */
    public SupplierResponse save(SupplierRequest supplierRequest) {
        Supplier supplier = supplierMapper.toEntity(supplierRequest);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    /**
     * Updates an existing {@link Supplier} entity.
     * <p>
     * This method retrieves an existing {@link Supplier} by its ID, maps the provided {@link SupplierRequest}
     * DTO to a {@link Supplier} entity, updates the existing entity, and saves the updated version in the database.
     * The updated {@link Supplier} is returned as a {@link SupplierResponse} DTO.
     *
     * @param id The ID of the {@link Supplier} to update.
     * @param supplierRequest The {@link SupplierRequest} DTO containing the updated data for the supplier.
     * @return The updated {@link Supplier} as a {@link SupplierResponse} DTO.
     */
    public SupplierResponse update(Integer id, SupplierRequest supplierRequest) {
        Supplier supplier = this.findById(id);
        Supplier updatedSupplier = supplierMapper.toEntity(supplierRequest);
        updatedSupplier.setId(supplier.getId());
        Supplier savedSupplier = supplierRepository.save(updatedSupplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    /**
     * Deletes a {@link Supplier} entity.
     * <p>
     * This method retrieves an existing {@link Supplier} by its ID, deletes it from the database, and
     * returns the deleted {@link Supplier} as a {@link SupplierResponse} DTO.
     *
     * @param id The ID of the {@link Supplier} to delete.
     * @return The deleted {@link Supplier} as a {@link SupplierResponse} DTO.
     */
    public SupplierResponse delete(Integer id) {
        Supplier supplier = this.findById(id);
        supplierRepository.delete(supplier);
        return supplierMapper.toDTO(supplier);
    }

    /**
     * Finds a {@link Supplier} by its ID.
     * <p>
     * This method retrieves a {@link Supplier} by its ID from the database. If no supplier is found with
     * the given ID, a {@link ResourceNotFoundException} is thrown.
     *
     * @param id The ID of the {@link Supplier} to retrieve.
     * @return The {@link Supplier} entity corresponding to the given ID.
     * @throws ResourceNotFoundException if no {@link Supplier} is found with the given ID.
     */
    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Proveedor no encontrado."));
    }

    /**
     * Retrieves a list of all {@link SupplierResponse} DTOs.
     * <p>
     * This method fetches all {@link Supplier} entities from the database, maps them to DTOs using the
     * {@link SupplierMapper}, and returns a list of {@link SupplierResponse} objects.
     *
     * @return A list of {@link SupplierResponse} DTOs.
     */
    public List<SupplierResponse> findAll() {
        List<Supplier> list = supplierRepository.findAll();
        return list.stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

}
