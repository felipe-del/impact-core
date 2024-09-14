package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.Building;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<Building, Integer> {
    @Query("SELECT b FROM Building b WHERE b.id = ?1")
    Building findBuildingBy(int id);
}
