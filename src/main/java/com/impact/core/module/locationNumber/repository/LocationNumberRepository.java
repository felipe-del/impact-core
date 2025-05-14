package com.impact.core.module.locationNumber.repository;

import com.impact.core.module.locationNumber.entity.LocationNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the LocationNumber entity.
 * <p>
 * This interface extends {@link JpaRepository} and provides additional methods to interact with the database
 * for managing {@link LocationNumber} entities. It is responsible for persisting and retrieving location number data.
 * </p>
 */
@Repository
public interface LocationNumberRepository extends JpaRepository<LocationNumber, Integer> {
}
