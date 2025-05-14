package com.impact.core.module.user.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserToken;
import com.impact.core.module.user.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Service for managing user verification or recovery tokens.
 * Handles token generation, validation, expiration, and persistence.
 */
@Service("userTokenService")
@RequiredArgsConstructor
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    // Token generation constants
    private String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private int TOKEN_LENGTH = 6;
    private Random random = new Random();
    private int EXPIRY_MINUTES = 30;

    /**
     * Retrieves a token by its value.
     *
     * @param token the token string
     * @return the UserToken entity
     * @throws ResourceNotFoundException if token is not found
     */
    public UserToken findByToken(String token) {
        return userTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("No existe token: " + token));
    }

    /**
     * Persists a token in the database.
     *
     * @param userToken the UserToken entity to save
     * @return the saved UserToken
     */
    public UserToken save(UserToken userToken) {
        return userTokenRepository.save(userToken);
    }

    /**
     * Deletes a token from the database.
     *
     * @param userToken the UserToken to delete
     */
    public void delete(UserToken userToken) {
        userTokenRepository.delete(userToken);
    }

    /**
     * Generates a new unique token for the specified user,
     * replacing any existing token, and saves it.
     *
     * @param user the user for whom to generate the token
     * @return the saved UserToken
     */
    public UserToken generateAndSaveTokenForUser(User user) {
        userTokenRepository.findByUser(user).ifPresent(this::delete);
        String token = generateUniqueToken();
        Instant expiryDate = generateExpiryDate();

        UserToken userToken = UserToken.builder()
                .user(user)
                .token(token)
                .expiryDate(expiryDate)
                .createdAt(Instant.now())
                .build();

        return save(userToken);
    }


    /**
     * Validates whether a token exists and is not expired.
     *
     * @param token the token string
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean validateToken(String token) {
        UserToken userToken = findByToken(token);
        return userToken.getExpiryDate().isAfter(Instant.now());
    }

    // =======================
    // Private utility methods
    // =======================

    /**
     * Generates a token that does not yet exist in the database.
     *
     * @return a unique token string
     */
    private String generateUniqueToken() {
        String token;
        do {
            token = generateRandomToken();
        } while (userTokenRepository.existsByToken(token));
        return token;
    }

    /**
     * Randomly generates a token string of fixed length using alphanumeric characters.
     *
     * @return a random token string
     */
    private String generateRandomToken() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(index));
        }
        return token.toString();
    }

    /**
     * Calculates the token expiration timestamp.
     *
     * @return the expiration time as an Instant
     */
    private Instant generateExpiryDate() {
        return Instant.now().plus(EXPIRY_MINUTES, ChronoUnit.MINUTES);
    }
}
