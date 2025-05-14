package com.impact.core.security.jwt;

import com.impact.core.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter the process incoming HTTP requests to extract and validate JSON tokens
 * <p>
 *     If a valid token is found, it sets up an authentication context with the user
 *     details. This ensures secure endpoints can identify and authorize the user properly
 * </p>
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Intercepts each request to extract and validate the JWT token.
     * If valid, sets up the user authentication in the {@link SecurityContextHolder}.
     * @param request
     * The HTTP request
     * @param response
     * The HTTP response
     * @param filterChain
     * The filter chain
     * @throws ServletException
     * If any servlet related exception occurs
     * @throws IOException
     * If any input or output exception occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authentication header
     * @param request
     * The HTTP request
     * @return
     * The JWT token if present and properly formatted, if not returns {@code null}
     */
    private String parseJwt(HttpServletRequest request) {

        String HEADER = "Authorization";
        String headerAuth = request.getHeader(HEADER);

        if (headerAuth == null) {
            logger.warn("Authorization header is missing");
            return null;
        }

        String PREFIX = "Bearer ";
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(PREFIX)) {
            return headerAuth.substring(7);
        }

        logger.warn("JWT token is missing or does not start with Bearer");
        return null;
    }
}
