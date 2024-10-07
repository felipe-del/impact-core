package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.BuildingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuildingLocationRepository extends JpaRepository<BuildingLocation, Integer> {
    @Query("SELECT bl FROM BuildingLocation bl WHERE bl.id = ?1")
    BuildingLocation findBuildingLocationBy(int id);
}
