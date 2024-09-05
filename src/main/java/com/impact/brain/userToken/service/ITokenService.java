package com.impact.brain.userToken.service;

import com.impact.brain.userToken.entity.UserToken;

/**
 * Interface for the TokenService, defining the operations related to token management.
 * <p>
 * This interface provides methods to save and find tokens.
 * </p>
 *
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:07 PM
 */
public interface ITokenService {

    /**
     * Saves a token to the repository.
     *
     * @param token the token to be saved.
     * @return the saved token.
     */
    UserToken saveToken(UserToken token);

    /**
     * Finds a token by its value.
     *
     * @param token the value of the token to be found.
     * @return the token with the specified value.
     */
    UserToken findByToken(String token);
}
