package com.impact.core;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Configures the servlet initializer for the Spring application
 * <p>
 *     This class is used when deploying the application to a servlet container
 *     as a traditional WAR file. It extends {@link SpringBootServletInitializer}
 *     and overrides the {@code configure} method to specify application sources
 * </p>
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configures the application when it is launched by a servlet container
     * @param application
     * The {@link SpringApplicationBuilder} is used to configure the application
     * @return
     * The configured {@link SpringApplicationBuilder} with the application source set
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ImpactCoreApplication.class);
    }

}
