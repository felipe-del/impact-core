package com.impact.core.module.user.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.mapper.MyUserMapper;
import com.impact.core.module.user.payload.request.UserRequest;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.repository.UserRepository;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

/**
 * Service layer for managing users and their related operations.
 */
@Service("userService")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MyUserMapper myUserMapper;

    /**
     * Finds a user by email for authentication purposes.
     *
     * @param email the user's email
     * @return the User entity
     * @throws UsernameNotFoundException if the user does not exist
     */
    public User findImpactUser(String email) { // Use only in login and authentication
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario con email: " + email));
    }

    /**
     * Finds a user by email for general use.
     *
     * @param email the user's email
     * @return the User entity
     * @throws ResourceNotFoundException if the user does not exist
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con email: " + email));
    }

    /**
     * Checks if a user exists by their email.
     *
     * @param email the user's email
     * @return true if the user exists, false otherwise
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Saves a new user to the database.
     *
     * @param user the user entity to save
     * @return the saved User entity
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates the name and email of an existing user.
     *
     * @param id    the user's ID
     * @param user  the request payload containing the updated data
     * @return a UserResponse with the updated information
     * @throws ResourceNotFoundException if the user does not exist
     */
    public UserResponse update(int id, UserRequest user) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        return myUserMapper.toDTO(userRepository.save(userToUpdate));
    }

    /**
     * Finds a user by ID.
     *
     * @param id the user's ID
     * @return the User entity
     * @throws ResourceNotFoundException if the user does not exist
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con id: " + id));
    }

    /**
     * Retrieves all users with the administrator role.
     *
     * @return a list of administrator users
     */
    public List<User> getAllAdmins() {
        return userRepository.findByRole_Name(EUserRole.ROLE_ADMINISTRATOR);
    }

    /**
     * Retrieves all users as DTOs.
     *
     * @return a list of UserResponse objects
     */
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(myUserMapper::toDTO)
                .toList();
    }
}
