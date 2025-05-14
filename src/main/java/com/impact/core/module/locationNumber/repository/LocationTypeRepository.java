package com.impact.core.module.locationNumber.repository;

import com.impact.core.module.locationNumber.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the LocationType entity.
 * <p>
 * This interface extends {@link JpaRepository} and provides additional methods to interact with the database
 * for managing {@link LocationType} entities. It is responsible for persisting and retrieving location type data.
 * </p>
 */
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Integer> {
}
