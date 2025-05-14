package com.impact.core.module.supplier.repository;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.enun.EEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing database operations on {@link EntityType} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard Create, Read, Update, and Delete (CRUD)
 * functionality for the {@link EntityType} table, along with custom query methods.
 */
@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, Long> {

    /**
     * Retrieves an {@link Optional} containing the {@link EntityType} that matches
     * the specified {@link EEntityType} name.
     *
     * @param name the {@link EEntityType} enumeration value to search by
     * @return an {@link Optional} containing the matching {@link EntityType}, if found
     */
    Optional<EntityType> findByTypeName(EEntityType name);
}
