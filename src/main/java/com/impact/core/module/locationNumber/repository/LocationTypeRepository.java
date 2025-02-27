package com.impact.core.module.locationNumber.repository;

import com.impact.core.module.locationNumber.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Integer> {
}
