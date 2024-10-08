package com.impact.brain.commonSpace.repository;

import com.impact.brain.commonSpace.entity.SpaceReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceReservationRepository extends JpaRepository<SpaceReservation, Integer> {
}
