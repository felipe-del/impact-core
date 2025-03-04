package com.impact.core.module.spaceRequest_Reservation.repository;

import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRequestRepository extends JpaRepository<SpaceRequest, Integer> {
}
