package com.impact.brain.userToken.repository;

import com.impact.brain.userToken.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:06 PM
 */
@Repository
public interface TokenRepository extends JpaRepository<UserToken, Integer> {
    UserToken findTokenByToken(String token);
}
