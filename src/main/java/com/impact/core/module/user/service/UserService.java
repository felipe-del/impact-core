package com.impact.core.module.user.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.payload.UserResponse;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User findImpactUser(String email) { // Use only in login and authentication
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario con email: " + email));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con email: " + email));
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con id: " + id));
    }

    public List<User> getAllAdmins() {
        return userRepository.findByRole_Name(EUserRole.ROLE_ADMINISTRATOR);
    }

    // MAPPER METHODS

    public UserResponse toDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roleName(user.getRole().getName().toString())
                .stateName(user.getState().getName().toString())
                .build();
    }

}
