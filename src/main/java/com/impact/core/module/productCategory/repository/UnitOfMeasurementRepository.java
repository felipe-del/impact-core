package com.impact.core.module.productCategory.repository;

import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Integer> {

}
