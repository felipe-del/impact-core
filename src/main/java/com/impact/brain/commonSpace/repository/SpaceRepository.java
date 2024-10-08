package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpaceRepository extends JpaRepository<Space, Integer> {
    @Query("SELECT sp FROM Space sp WHERE sp.id = ?1")
    Space findSpaceById(int id);
}
