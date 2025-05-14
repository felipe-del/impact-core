package com.impact.core.module.supplier.repository;

import com.impact.core.module.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing database operations on {@link Supplier} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard Create, Read, Update, and Delete (CRUD)
 * functionality for the {@link Supplier} table.
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
