package com.impact.core.module.resource_request_status.repository;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link ResourceRequestStatus} entities.
 * <p>
 * This interface provides the necessary methods to interact with the database for the resource request statuses.
 * It extends {@link JpaRepository} to leverage basic CRUD operations and adds a custom method to find a status by its name.
 */
@Repository
public interface ResourceRequestStatusRepository extends JpaRepository<ResourceRequestStatus, Integer> {

    /**
     * Finds a {@link ResourceRequestStatus} by its name.
     * <p>
     * This method allows querying the database for a resource request status based on the enum value of its name.
     * The status name is represented by the {@link EResourceRequestStatus} enum.
     *
     * @param name the name of the resource request status.
     * @return an {@link Optional} containing the matching {@link ResourceRequestStatus} if found, or an empty {@link Optional} if not.
     */
    Optional<ResourceRequestStatus> findByName(EResourceRequestStatus name);
}
