package com.impact.brain.auth.service;

import com.impact.brain.configuration.exception.customError.NotFoundException;
import com.impact.brain.configuration.jwt.JwtProperties;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.entity.UserJwtToken;
import com.impact.brain.user.service.userJwtToken.UserJwtTokenServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service("jwtService")
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtProperties jwtProperties;
    private final UserJwtTokenServiceImpl userJwtTokenService;

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
        userJwtTokenService.save(new UserJwtToken(
                null,
                user,
                token,
                Instant.now().plusMillis(jwtProperties.getExpiration()),
                Instant.now()
        ));
        return token;
    }

    @Override
    public boolean validateToken(String token) {
        return !isTokenExpired(token) && !isTokenBlacklisted(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    private boolean isTokenBlacklisted(String token) {
        Optional<UserJwtToken> userJwtTokenOpt = userJwtTokenService.findByToken(token);
        return userJwtTokenOpt.isPresent() && userJwtTokenOpt.get().getExpiryDate().isBefore(Instant.now());
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    @Override
    public String getUserEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public void blacklistToken(String token) {
        Optional<UserJwtToken> userJwtTokenOpt = userJwtTokenService.findByToken(token);
        if (userJwtTokenOpt.isEmpty()) {
            throw new NotFoundException("Token not found");
        }
        userJwtTokenService.delete(userJwtTokenOpt.get());
    }
}
