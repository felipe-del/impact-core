package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query("SELECT b FROM Building b WHERE b.id = ?1")
    Building findBuildingBy(int id);
}
