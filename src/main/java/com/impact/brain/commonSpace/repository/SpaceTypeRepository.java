package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.SpaceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpaceTypeRepository extends CrudRepository<SpaceType, Integer> {
    @Query("SELECT st FROM SpaceType st WHERE st.id = ?1")
    SpaceType findBySpaceTypeId(int id);
}
