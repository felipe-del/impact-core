package com.impact.brain.supplier.service;

import com.impact.brain.supplier.dto.SupplierDTO;
import com.impact.brain.supplier.entity.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:47 AM
 */
public interface ISupplierService {
    List<SupplierDTO> getSuppliers();
    Supplier save(SupplierDTO supplier);
    Supplier getById(int id);
}
