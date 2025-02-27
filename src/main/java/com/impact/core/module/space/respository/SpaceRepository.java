package com.impact.core.module.space.respository;

import com.impact.core.module.space.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {
}
