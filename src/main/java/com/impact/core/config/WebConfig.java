package com.impact.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin-Resource-Sharing (CORS) settings for the IMPACT application.
 * <p>
 *     This class allows the frontend (or the specified origin) to interact with the backend
 *     by enabling CORS for all endpoints and common HTTP methods
 * </p>
 * The list of allowed origins is injected from the application configuration property {@code impact.cors.allowedOrigins}
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${impact.cors.allowedOrigins}")
    private String allowedOrigins;

    /**
     * Adds global CORS mappings to the application, allowing cross-origin requests to all endpoints.
     * <p>
     *     The allowed origin(s), HTTP methods, and headers are configurable. Credentials are also allowed,
     *     enabling cookie or token-based authentication from frontend clients.
     * </p>
     * @param registry
     * the CORS registry used to define the cors-origin settings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
