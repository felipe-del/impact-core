package com.impact.brain.security;

import com.impact.brain.user.entity.User;
import com.impact.brain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 12:42 PM
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new UserDetailsImp(user);
    }
}
