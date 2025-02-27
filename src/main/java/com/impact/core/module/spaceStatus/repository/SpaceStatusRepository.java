package com.impact.core.module.spaceStatus.repository;

import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceStatusRepository extends JpaRepository<SpaceStatus, Integer> {
    Optional<SpaceStatus> findByName(ESpaceStatus name);
}
