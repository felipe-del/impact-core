package com.impact.brain.user.repository;

import com.impact.brain.user.entity.UserJwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJwtTokenRepository extends JpaRepository<UserJwtToken, Integer> {
    Optional<UserJwtToken> findByToken(String token);
}
