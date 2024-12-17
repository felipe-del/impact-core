package com.impact.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configures CORS globally for all endpoints
        registry.addMapping("/**")  // Applies CORS to all endpoints
                .allowedOrigins("*")  // Allows all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // Allows these methods
                .allowedHeaders("*")  // Allows all headers
                .allowCredentials(true)  // Allows the use of credentials (cookies, authentication)
                .maxAge(3600);  // The time in seconds that the CORS configuration is cached (1 hour)
    }
}