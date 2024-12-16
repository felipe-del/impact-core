package com.impact.brain.user.service.userJwtToken;

import com.impact.brain.user.entity.UserJwtToken;
import com.impact.brain.user.repository.UserJwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userJwtTokenService")
@RequiredArgsConstructor
public class UserJwtTokenServiceImpl implements UserJwtTokenService {
    private final UserJwtTokenRepository userJwtTokenRepository;

    @Override
    public UserJwtToken save(UserJwtToken userJwtToken) {
        return userJwtTokenRepository.save(userJwtToken);
    }

    @Override
    public void delete(UserJwtToken userJwtToken) {
        userJwtTokenRepository.delete(userJwtToken);
    }

    @Override
    public Optional<UserJwtToken> findByToken(String token) {
        return userJwtTokenRepository.findByToken(token);
    }
}
