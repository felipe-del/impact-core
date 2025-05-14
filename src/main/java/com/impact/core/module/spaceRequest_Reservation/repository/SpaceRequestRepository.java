package com.impact.core.module.spaceRequest_Reservation.repository;

import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing space requests in the database.
 * This interface provides methods for querying and modifying space request entities.
 */
@Repository
public interface SpaceRequestRepository extends JpaRepository<SpaceRequest, Integer> {

    /**
     * Retrieves a list of space requests made by a specific user.
     * <p>
     * This query selects all space requests where the user ID matches the provided userId.
     *
     * @param userId The ID of the user whose space requests are to be fetched.
     * @return A list of {@link SpaceRequest} entities associated with the specified user.
     */
    @Query("SELECT s FROM SpaceRequest s WHERE s.user.id = :userId")
    List<SpaceRequest> spaceRequestByUser(@Param("userId") Integer userId);

    /**
     * Retrieves a list of space requests with a specific status.
     * <p>
     * This query selects all space requests where the status ID matches the provided status.
     *
     * @param status The ID of the status to filter the space requests by.
     * @return A list of {@link SpaceRequest} entities that have the specified status.
     */
    @Query("SELECT s FROM SpaceRequest s WHERE s.status.id = :status")
    List<SpaceRequest> spaceRequestByStatus(@Param("status") Integer status);

    /**
     * Updates the status of a specific space request.
     * <p>
     * This query updates the status ID of the space request identified by the requestId with the new statusId.
     *
     * @param statusId The new status ID to set for the space request.
     * @param requestId The ID of the space request whose status is to be updated.
     */
    @Modifying
    @Query("UPDATE SpaceRequest r SET r.status.id = :statusId WHERE r.id= :requestId")
    void updateSpaceRequest(@Param("statusId") Integer statusId, @Param("requestId") Integer requestId);
}
