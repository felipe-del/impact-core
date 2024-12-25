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

@Service("supplierService")
@RequiredArgsConstructor
public class SupplierService {
    public final SupplierRepository supplierRepository;
    public final SupplierMapper supplierMapper;

    public Supplier save(SupplierRequest supplierRequest) {
        Supplier supplier = toEntity(supplierRequest);
        return supplierRepository.save(supplier);
    }

    public Supplier update(Integer id, SupplierRequest supplierRequest) {
        Supplier supplier = this.findById(id);
        Supplier updatedSupplier = toEntity(supplierRequest);
        updatedSupplier.setId(supplier.getId());
        return supplierRepository.save(updatedSupplier);
    }

    public Supplier delete(Integer id) {
        Supplier supplier = this.findById(id);
        supplierRepository.delete(supplier);
        return supplier;
    }

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Proveedor no encontrado."));
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    // MAPPER METHODS

    public Supplier toEntity(SupplierRequest supplierRequest) {
        return supplierMapper.toEntity(supplierRequest);
    }

    public SupplierResponse toDTO(Supplier supplier) {
        return supplierMapper.toDTO(supplier);
    }

}
