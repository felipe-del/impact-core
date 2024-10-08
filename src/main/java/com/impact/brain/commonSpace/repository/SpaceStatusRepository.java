package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.SpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpaceStatusRepository extends JpaRepository<SpaceStatus, Integer> {
    @Query("SELECT st FROM SpaceStatus st WHERE st.id = ?1")
    SpaceStatus findSpaceStatusById(int id);
}
