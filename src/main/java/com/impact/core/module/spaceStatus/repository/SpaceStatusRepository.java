package com.impact.core.module.spaceStatus.repository;

import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing {@link SpaceStatus} entities.
 * <p>
 * This interface extends {@link JpaRepository}, which provides the standard methods for interacting with the database.
 * Additionally, it contains a custom method to find a {@link SpaceStatus} by its name, represented by the {@link ESpaceStatus} enum.
 */
@Repository
public interface SpaceStatusRepository extends JpaRepository<SpaceStatus, Integer> {

    /**
     * Retrieves a {@link SpaceStatus} entity by its name.
     * <p>
     * This method queries the database for a {@link SpaceStatus} entity with the specified {@code name}. The name is represented
     * by the {@link ESpaceStatus} enum. If no entity with the specified name exists, it returns an empty {@link Optional}.
     *
     * @param name The name of the {@link SpaceStatus} entity (of type {@link ESpaceStatus}).
     * @return An {@link Optional} containing the {@link SpaceStatus} entity if found, otherwise an empty {@link Optional}.
     */
    Optional<SpaceStatus> findByName(ESpaceStatus name);
}
