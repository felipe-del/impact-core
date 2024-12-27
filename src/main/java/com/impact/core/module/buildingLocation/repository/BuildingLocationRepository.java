package com.impact.core.module.buildingLocation.repository;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingLocationRepository extends JpaRepository<BuildingLocation, Integer> {
}
