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

@Service("supplierService")
@RequiredArgsConstructor
public class SupplierService {
    public final SupplierRepository supplierRepository;
    public final SupplierMapper supplierMapper;

    public SupplierResponse save(SupplierRequest supplierRequest) {
        Supplier supplier = supplierMapper.toEntity(supplierRequest);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    public SupplierResponse update(Integer id, SupplierRequest supplierRequest) {
        Supplier supplier = this.findById(id);
        Supplier updatedSupplier = supplierMapper.toEntity(supplierRequest);
        updatedSupplier.setId(supplier.getId());
        Supplier savedSupplier = supplierRepository.save(updatedSupplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    public SupplierResponse delete(Integer id) {
        Supplier supplier = this.findById(id);
        supplierRepository.delete(supplier);
        return supplierMapper.toDTO(supplier);
    }

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Proveedor no encontrado."));
    }

    public List<SupplierResponse> findAll() {
        List<Supplier> list = supplierRepository.findAll();
        return list.stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

}
