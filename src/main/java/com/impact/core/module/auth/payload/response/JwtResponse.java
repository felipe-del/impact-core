package com.impact.core.module.auth.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response class representing a JSON Web Token (JWT) issued after a successful authentication request.
 * <p>
 * This Data Transfer Object (DTO) includes the access token, token type, user identifier,
 * username, email, and associated roles.
 * <p>
 * Typically returned by authentication endpoints to allow authorized access to secured resources.
 */
@Setter
@Getter
@NoArgsConstructor
public class JwtResponse {

    /**
     * The issued access token.
     */
    private String token;

    /**
     * The type of the token, defaulted to {@code Bearer}.
     */
    private String type = "Bearer";

    /**
     * The unique identifier of the authenticated user.
     */
    private Integer id;

    /**
     * The username of the authenticated user.
     */
    private String username;

    /**
     * The email address of the authenticated user.
     */
    private String email;

    /**
     * The list of role names granted to the authenticated user.
     */
    private List<String> roles;

    /**
     * Constructs a new {@code JwtResponse} with the specified authentication data.
     *
     * @param accessToken the issued JWT
     * @param id the user's unique identifier
     * @param username the user's username
     * @param email the user's email
     * @param roles the list of granted roles
     */
    public JwtResponse(String accessToken, Integer id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
