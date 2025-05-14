package com.impact.core.module.space.respository;

import com.impact.core.module.space.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing persistence operations on {@link Space} entities.
 * <p>
 * Extends {@link JpaRepository} to inherit standard Create, Read, Update, and Delete (CRUD)
 * methods for the {@link Space} entity using an {@code Integer} as the primary key type.
 */
@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {
}
