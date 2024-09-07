package com.impact.brain.user.service.impl;

import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.email.service.impl.EmailSendService;
import com.impact.brain.email.util.EmailServiceUtil;
import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.intity.User;
import com.impact.brain.user.repository.UserRepository;
import com.impact.brain.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:27 AM
 */
@Service("userService")
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final EmailSendService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, EmailSendService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws Exception {
        // Encriptar la contraseña con BCrypt
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        //user.setRole("TEACHER");
        //user.setState((byte) 0);

        User savedUser = this.userRepository.save(user);
        savedUser.setPassword(null);

        // Preparar y enviar correo de bienvenida
        SendRequest sendRequest = EmailServiceUtil.prepareWelcomeEmail(savedUser);
        emailService.sendMessage(sendRequest, true);


        return savedUser;
    }
    @Override
    public User changePassword(int userId, String newPassword) {
        // Find the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Update user's password
        user.setPassword(passwordEncoder.encode(newPassword)); // Encode the password before saving

        // Save the updated user entity
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                user.getState().getName()
        );
    }
}
