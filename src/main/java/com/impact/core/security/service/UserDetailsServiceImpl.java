package com.impact.core.security.service;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}, responsible
 * for loading user-specific data during the authentication process.
 * <p>
 *     This class delegates user retrieval to {@link UserService}, and maps
 *     the resulting {@link User} to a {@link UserDetailsImpl} instance
 * </p>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * Loads a user's authentication details by their username
     * <p>
     *     This method is used by Spring Security to validate the user's credentials.
     *     It retrieves the user using {@link UserService} and wraps the result in
     *     a {@link UserDetailsImpl} instance.
     * </p>
     * @param username
     * The username or identifier to find the user
     * @return
     * A fully populated {@link UserDetailsImpl} object for authentication
     * @throws UsernameNotFoundException
     * If it fails to find a user with the corresponding username
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findImpactUser(username);

        return UserDetailsImpl.build(user);
    }

}