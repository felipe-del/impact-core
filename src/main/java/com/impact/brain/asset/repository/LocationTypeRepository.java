package com.impact.brain.asset.repository;

import com.impact.brain.asset.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/22/2024 - 8:25 PM
 */
public interface LocationTypeRepository extends JpaRepository<LocationType, Integer> {
    Optional<LocationType> findById(Integer id);
}
