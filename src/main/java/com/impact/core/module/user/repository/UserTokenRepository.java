package com.impact.core.module.user.repository;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByToken(String token);
    Optional<UserToken> findByUser(User user);
    boolean existsByToken(String token);
}
