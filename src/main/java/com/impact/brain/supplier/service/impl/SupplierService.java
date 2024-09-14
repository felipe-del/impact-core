package com.impact.brain.supplier.service.impl;

import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.repository.SupplierRepository;
import com.impact.brain.supplier.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:48 AM
 */
@Service
public class SupplierService implements ISupplierService {
    @Autowired
    public final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Iterable<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> getById(int id) {
        return supplierRepository.findById(id);
    }
}
