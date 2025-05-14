package com.impact.core.module.user.repository;

import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for UserRole entity.
 * Provides methods to retrieve user roles by enum name or ID.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Finds a user role by its enum name.
     *
     * @param name the EUserRole enum value
     * @return an Optional containing the UserRole if found, otherwise empty
     */
    Optional<UserRole> findByName(EUserRole name);

    /**
     * Finds a user role by its ID.
     *
     * @param id the ID of the UserRole
     * @return an Optional containing the UserRole if found, otherwise empty
     */
    Optional<UserRole> findById(int id);

    /**
     * Retrieves all user roles from the database.
     *
     * @return list of all UserRole entities
     */
    List<UserRole> findAll();
}
