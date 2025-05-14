package com.impact.core.module.user.repository;

import com.impact.core.module.user.enun.EUserState;
import com.impact.core.module.user.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserState entity.
 * Provides methods to find user states by enum name or ID.
 */
@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {

    /**
     * Finds a user state by its enum name.
     *
     * @param name the EUserState enum value
     * @return an Optional containing the UserState if found, otherwise empty
     */
    Optional<UserState> findByName(EUserState name);

    /**
     * Finds a user state by its ID.
     *
     * @param id the ID of the UserState
     * @return an Optional containing the UserState if found, otherwise empty
     */
    Optional<UserState> findById(int id);
}
