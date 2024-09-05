package com.impact.brain.configuration.emailConfig;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * Configuration class for providing CORS (Cross-Origin Resource Sharing) configuration.
 * <p>
 * This class implements the {@link CorsConfigurationSource} interface to supply custom
 * CORS settings for the application. CORS configuration is used to control how resources
 * on a server can be requested from another domain.
 * </p>
 *
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 11:51 AM
 */
@Configuration
public class CorsConfigSourceImpl implements CorsConfigurationSource {

    /**
     * Provides the CORS configuration for handling cross-origin requests.
     * <p>
     * This method configures which headers, origins, and methods are allowed in cross-origin requests.
     * It also specifies whether credentials are allowed and which headers should be exposed.
     * </p>
     *
     * @param request the {@link HttpServletRequest} for which CORS configuration is requested.
     * @return a {@link CorsConfiguration} object containing the CORS settings.
     */
    @Override
    public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Specifies the headers that are allowed in CORS requests.
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        // Specifies the origin patterns allowed to make CORS requests.
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:5173"));

        // Specifies the HTTP methods allowed in CORS requests.
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Specifies whether credentials (e.g., cookies, authorization headers) are allowed.
        corsConfiguration.setAllowCredentials(true);

        // Specifies the headers that are exposed to the client.
        corsConfiguration.setExposedHeaders(List.of("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        return corsConfiguration;
    }
}
