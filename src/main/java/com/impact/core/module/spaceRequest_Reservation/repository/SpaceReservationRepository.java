package com.impact.core.module.spaceRequest_Reservation.repository;

import com.impact.core.module.space.entity.Space;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing space reservations in the database.
 * This interface provides methods for querying space reservations related to spaces and users.
 */
@Repository
public interface SpaceReservationRepository extends JpaRepository<SpaceReservation, Integer> {

    /**
     * Retrieves a list of space reservations associated with a specific space.
     * <p>
     * This method returns all space reservations linked to the provided space entity.
     *
     * @param space The {@link Space} entity for which to fetch reservations.
     * @return A list of {@link SpaceReservation} entities associated with the specified space.
     */
    List<SpaceReservation> findAllBySpace(Space space);

    /**
     * Retrieves a list of space reservations made by a specific user.
     * <p>
     * This query selects all space reservations where the user ID matches the provided userId.
     *
     * @param userId The ID of the user whose space reservations are to be fetched.
     * @return A list of {@link SpaceReservation} entities associated with the specified user.
     */
    @Query("SELECT s FROM SpaceReservation s WHERE s.user.id = :userId")
    List<SpaceReservation> spaceReservationByUser(@Param("userId") Integer userId);

}
