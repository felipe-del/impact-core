package com.impact.core.security.jwt;

import com.impact.core.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for generating, parsing, validating, and invalidating JWT tokens.
 * <p>
 *     This class also handles token blacklisting for manual invalidation of JWTs.
 * </p>
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${impact.jwt.secret}")
    private String jwtSecret;

    @Value("${impact.jwt.expirationMs}")
    private int jwtExpirationMs;

    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    /**
     * Generates a JWT token for an authenticated user
     * @param authentication
     * the Spring Security {@link Authentication} object
     * @return
     * A signed JWT token
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Returns the signing key used to validate or sign the JWT token
     * @return
     * a {@link Key} object derived from the configured secret
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extracts username (subject) from the JWT token.
     * @param token
     * the JWT token
     * @return
     * The subject (username - email)
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates the integrity and expiration of JWT token.
     * Also checks if the token is blacklisted.
     * @param authToken
     * The JWT token to check
     * @return
     * {@code true} if the token is valid and not blacklisted, {@code false} otherwise
     */
    public boolean validateJwtToken(String authToken) {
        if (isBlacklisted(authToken)) {
            logger.error("JWT token is blacklisted.");
            return false;
        }

        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Checks whether a token has been blacklisted
     * @param token
     * The JWT token
     * @return
     * {@code true} if the token is contained in the blacklist, {@code false} otherwise
     */
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    /**
     * Manually invalidates a JWT token blacklisting it
     * @param token
     * The JWT token to invalidate
     */
    public void invalidateJwtToken(String token) {
        blacklist.add(token);
        String username = getUserNameFromJwtToken(token);
        logger.info("Token invalidated for user: {}", username);
    }
}
