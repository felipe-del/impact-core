package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.SpaceStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpaceStatusRepository extends CrudRepository<SpaceStatus, Integer> {
    @Query("SELECT st FROM SpaceStatus st WHERE st.id = ?1")
    SpaceStatus findSpaceStatusById(int id);
}
