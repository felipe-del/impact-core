package com.impact.core.module.buildingLocation.repository;

import com.impact.core.module.buildingLocation.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Building} entities.
 * <p>
 * Provides standard Create, Read, Update, and Delete (CRUD) operations
 * for {@link Building} using Spring Data JPA.
 */
@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
