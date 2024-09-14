package com.impact.brain.supplier.service;

import com.impact.brain.supplier.entity.Supplier;

import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:47 AM
 */
public interface ISupplierService {
    Iterable<Supplier> getSuppliers();
    Supplier save(Supplier supplier);
    Optional<Supplier> getById(int id);
}
