package com.impact.core.module.locationNumber.repository;

import com.impact.core.module.locationNumber.entity.LocationNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationNumberRepository extends JpaRepository<LocationNumber, Integer> {
}
