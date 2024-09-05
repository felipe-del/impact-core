package com.impact.brain.userToken.service.impl;

import com.impact.brain.email.service.impl.EmailSendService;
import com.impact.brain.userToken.entity.UserToken;
import com.impact.brain.userToken.repository.TokenRepository;
import com.impact.brain.userToken.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the ITokenService interface.
 * <p>
 * This class provides concrete implementations for saving and finding tokens, and it is
 * annotated with {@link Service} to be recognized as a Spring service component.
 * </p>
 *
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:07 PM
 */
@Service
public class TokenService implements ITokenService {

    private final TokenRepository tokenRepository;
    private final EmailSendService emailService;

    @Autowired
    public TokenService(TokenRepository tokenRepository, EmailSendService emailService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserToken saveToken(UserToken token) {
        return tokenRepository.save(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserToken findByToken(String token) {
        return tokenRepository.findTokenByToken(token);
    }
}
