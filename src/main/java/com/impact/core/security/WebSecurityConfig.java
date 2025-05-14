package com.impact.core.security;

import com.impact.core.security.exceptionHandling.AccessDeniedHandlerJwt;
import com.impact.core.security.jwt.AuthEntryPointJwt;
import com.impact.core.security.jwt.AuthTokenFilter;
import com.impact.core.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures Spring Security for the IMPACT application using JWT-based authentication
 * <p> This class enables method-level security and sets up the security context for handling
 * authentication and authorization. Key configurations include: <p/>
 * <ul>
 *     <li>Integration of a custom JWT authentication filter</li>
 *     <li>Stateless session management support token-based authentication</li>
 *     <li>Custom handlers for unauthorized (401) and access-denied (403) responses</li>
 *     <li>Password encoding using BCrypt</li>
 *     <li>DAO-based authentication with the custom {@code UserDetailsServiceImpl}</li>
 * </ul>
 * <p>
 *     ALl HTTP requests are permitted by default at the filter level, as access control is
 *     enforced at the controller level using method-level security annotations (e.g, {@code @PreAuthorize})
 * </p>
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private AccessDeniedHandlerJwt accessDeniedHandlerJwt;

    /**
     * Defines the custom JWT authentication filter used to intercept and validate
     * JWT tokens in incoming HTTP requests
     * @return
     * The configured {@link AuthTokenFilter} bean
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configures a {@link DaoAuthenticationProvider} that uses a custom
     * {@link UserDetailsServiceImpl} and a {@link BCryptPasswordEncoder} to authenticate users
     * @return
     * The configured authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Exposes the {@link AuthenticationManager} from Spring's {@link AuthenticationConfiguration},
     * enabling its injection into other components
     * @param authConfig
     * Spring's authentication configuration
     * @return
     * The application's {@link AuthenticationManager}
     * @throws Exception
     * If the authentication configuration process fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configures the {@code PasswordEncoder} to use a BCrypt hashing
     * algorithm. This encoder is used to has and verify passwords safely.
     * @return
     * A {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the core security Spring filter chain for HTTP request handling.
     * <p> Key settings include the following:
     * <ul>
     *     <li>Disabling CSRF protection due to the use of JWTs</li>
     *     <li>Stateless session management</li>
     *     <li>Custom exception handlers for unauthorized and access-denied responses</li>
     *     <li>Integration of the custom JWT authentication filter</li>
     * </ul>
     * @param http
     * The {@link HttpSecurity} object provided by spring
     * @return
     * The configured {@link SecurityFilterChain}
     * @throws Exception
     * If configuration fails at any point
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler) // Custom handler for 401 unauthorized
                        .accessDeniedHandler(accessDeniedHandlerJwt) // Custom handler for 403 forbidden
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
