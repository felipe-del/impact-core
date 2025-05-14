package com.impact.core.module.buildingLocation.repository;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link BuildingLocation} entities.
 * <p>
 * Provides standard Create, Read, Update, and Delete (CRUD) operations
 * for {@link BuildingLocation} using Spring Data JPA.
 */
@Repository
public interface BuildingLocationRepository extends JpaRepository<BuildingLocation, Integer> {
}
