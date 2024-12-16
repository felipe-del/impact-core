package com.impact.brain.user.service.userJwtToken;

import com.impact.brain.user.entity.UserJwtToken;

import java.util.Optional;

public interface UserJwtTokenService {
    UserJwtToken save(UserJwtToken userJwtToken);
    void delete(UserJwtToken userJwtToken);
    Optional<UserJwtToken> findByToken(String token);
}
