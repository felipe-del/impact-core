package com.impact.core.module.spaceRequest_Reservation.repository;

import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceReservationRepository extends JpaRepository<SpaceReservation, Integer> {
    @Query("SELECT s FROM SpaceReservation s WHERE s.user.id = :userId")
    List<SpaceReservation> spaceReservationByUser(@Param("userId") Integer userId);
}
