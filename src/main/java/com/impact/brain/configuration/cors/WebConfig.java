package com.impact.brain.configuration.cors;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) in the application.
 * <p>
 * This class implements the {@link WebMvcConfigurer} interface to provide custom configuration for
 * CORS. It allows cross-origin requests from specified origins and defines which HTTP methods are
 * permitted.
 * </p>
 *
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 11:53 AM
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     * <p>
     * This method defines the CORS settings for the application, specifying which origins and HTTP
     * methods are allowed for cross-origin requests. It ensures that the frontend application running
     * on a specific origin can interact with the backend server.
     * </p>
     *
     * @param registry the {@link CorsRegistry} object used to configure CORS settings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
