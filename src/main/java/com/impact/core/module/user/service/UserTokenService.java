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

@Service("userTokenService")
@RequiredArgsConstructor
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;
    // Token generation
    private String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private int TOKEN_LENGTH = 6;
    private Random random = new Random();
    private int EXPIRY_MINUTES = 30;


    public UserToken findByToken(String token) {
        return userTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("No existe token: " + token));
    }

    public UserToken save(UserToken userToken) {
        return userTokenRepository.save(userToken);
    }

    public void delete(UserToken userToken) {
        userTokenRepository.delete(userToken);
    }

    public UserToken generateAndSaveTokenForUser(User user) {
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

    public boolean validateToken(String token) {
        UserToken userToken = findByToken(token);
        return userToken.getExpiryDate().isAfter(Instant.now());
    }

    // Private methods

    private String generateUniqueToken() {
        String token;
        do {
            token = generateRandomToken();
        } while (userTokenRepository.existsByToken(token));
        return token;
    }

    private String generateRandomToken() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(index));
        }
        return token.toString();
    }

    private Instant generateExpiryDate() {
        return Instant.now().plus(EXPIRY_MINUTES, ChronoUnit.MINUTES);
    }
}
