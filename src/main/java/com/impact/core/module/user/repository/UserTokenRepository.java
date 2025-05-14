package com.impact.core.module.user.repository;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserToken entity.
 * Provides methods to find, save, and check existence of user tokens.
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    /**
     * Finds a UserToken by its token string.
     *
     * @param token the token string
     * @return an Optional containing the UserToken if found, otherwise empty
     */
    Optional<UserToken> findByToken(String token);

    /**
     * Finds a UserToken by its associated User.
     *
     * @param user the user associated with the token
     * @return an Optional containing the UserToken if found, otherwise empty
     */
    Optional<UserToken> findByUser(User user);

    /**
     * Checks if a UserToken with the given token exists.
     *
     * @param token the token string
     * @return true if the token exists, false otherwise
     */
    boolean existsByToken(String token);
}
