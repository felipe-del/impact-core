package com.impact.core.module.productCategory.repository;

import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link UnitOfMeasurement} entities.
 * Extends {@link JpaRepository} to provide CRUD operations on {@link UnitOfMeasurement} entities.
 */
@Repository
public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Integer> {

}
