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

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private AccessDeniedHandlerJwt accessDeniedHandlerJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler) // Handler 401 Unauthorized
                        .accessDeniedHandler(accessDeniedHandlerJwt) // Handler 403 Forbidden
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        // ASSET BIG MODULE
                        .requestMatchers("/api/supplier/**").authenticated()
                        .requestMatchers("/api/entity-type/**").authenticated()
                        .requestMatchers("/api/asset-category/**").authenticated()
                        .requestMatchers("/api/asset-sub-category/**").authenticated()
                        .requestMatchers("/api/brand/**").authenticated()
                        .requestMatchers("/api/asset-model/**").authenticated()
                        .requestMatchers("/api/location-number/**").authenticated()
                        .requestMatchers("/api/location-type/**").authenticated()
                        .requestMatchers("/api/currency/**").authenticated()
                        .requestMatchers("/api/asset-status/**").authenticated()

                        .requestMatchers("/api/test/auth/**").permitAll()
                        .requestMatchers("/api/test/mail/**").permitAll()
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
