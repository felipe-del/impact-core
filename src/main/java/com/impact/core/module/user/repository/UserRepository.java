package com.impact.core.module.user.repository;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.enun.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and custom query methods for user-related access.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email.
     *
     * @param email the user's email
     * @return an Optional containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user with the given email exists.
     *
     * @param email the user's email
     * @return true if the user exists, false otherwise
     */
    Boolean existsByEmail(String email);

    /**
     * Finds a user by their ID.
     * This overrides the inherited method with a return type of Optional<User>.
     *
     * @param id the user's ID
     * @return an Optional containing the user if found, otherwise empty
     */
    Optional<User> findById(Integer id);

    /**
     * Retrieves all users that have a specific role.
     *
     * @param role the user role to filter by
     * @return list of users with the given role
     */
    List<User> findByRole_Name(EUserRole role);
}
