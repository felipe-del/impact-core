package com.impact.core.module.buildingLocation.repository;

import com.impact.core.module.buildingLocation.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
