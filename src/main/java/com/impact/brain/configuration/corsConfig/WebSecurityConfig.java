package com.impact.brain.configuration.corsConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * Configuration class for setting up web security for the application.
 * <p>
 * This class configures the security settings for the web application, including authorization rules,
 * exception handling, CSRF protection, and CORS settings. It uses Spring Security to enforce security
 * constraints and handle authentication and authorization.
 * </p>
 *
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 12:32 PM
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Configures security settings for HTTP requests.
     * <p>
     * This method sets up authorization rules for different URL patterns, configures exception handling
     * for unauthorized access, disables CSRF protection, and applies CORS settings using the provided
     * {@link CorsConfigSourceImpl}.
     * </p>
     *
     * @param httpSecurity the {@link HttpSecurity} object used to configure HTTP security.
     * @param corsConfigurationSourceImpl the {@link CorsConfigSourceImpl} instance used for CORS settings.
     * @return a {@link SecurityFilterChain} instance with the configured security settings.
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigSourceImpl corsConfigurationSourceImpl) throws Exception {
        httpSecurity
                // Configure authorization rules for different URL patterns.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/health/**").permitAll()
                        .requestMatchers("/email/**").permitAll() // Allow access to email API endpoints for all users.
                        .requestMatchers("/auth/**").permitAll() // Allow access to authentication API endpoints for all users.
                        .requestMatchers("/user/register").permitAll() // Allow access to user registration endpoint for all users.
                        .requestMatchers("/user/**").hasAnyAuthority("Administrator") // Restrict access to user endpoints to ADMINISTRATOR and MANAGER roles.
                        .requestMatchers("/product/**").hasAnyAuthority("Administrator", "Manager")// Restrict access to product endpoints to ADMINISTRATOR and MANAGER roles.
                )
                // Configure exception handling for unauthorized access.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Respond with HTTP 401 Unauthorized for unauthorized access.
                )
                // Disable CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Configure CORS settings.
                .cors(cors -> cors.configurationSource(corsConfigurationSourceImpl));

        return httpSecurity.build(); // Build and return the SecurityFilterChain instance.
    }

    /**
     * Provides a {@link PasswordEncoder} bean for encoding passwords.
     * <p>
     * This method returns an instance of {@link BCryptPasswordEncoder} for password encoding. It uses
     * the BCrypt hashing algorithm to securely encode passwords.
     * </p>
     *
     * @return a {@link PasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Return an instance of BCryptPasswordEncoder for password encoding.
    }
}
