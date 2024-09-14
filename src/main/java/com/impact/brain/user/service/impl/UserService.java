package com.impact.brain.user.service.impl;

import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.email.service.impl.EmailSendService;
import com.impact.brain.email.util.EmailServiceUtil;
import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.entity.UserRole;
import com.impact.brain.user.entity.UserState;
import com.impact.brain.user.repository.UserRepository;
import com.impact.brain.user.repository.UserRoleRepository;
import com.impact.brain.user.repository.UserStateRepository;
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
    private final UserStateRepository userStateRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository,UserStateRepository userStateRepository,UserRoleRepository userRoleRepository, EmailSendService emailService) {
        this.userRepository = userRepository;
        this.userStateRepository = userStateRepository;
        this.userRoleRepository = userRoleRepository;
        this.emailService = emailService;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws Exception {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            throw new Exception("Email ya registrado " + user.getEmail());
        }

        /** Encrypt password with BCrypt */
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        /** Search state "inactive" on DB */
        if (user.getState() == null) {
            UserState userState = userStateRepository.findByName("inactive");
            if (userState != null) {
                user.setState(userState);
            } else {
                throw new Exception("Estado 'inactive' no encontrado en la base de datos");
            }
        }
        /** Search rol "Teacher" on DB */
        if(user.getRole() == null) {
            UserRole userRole = userRoleRepository.findByName("Teacher");
            if(userRole != null) {
                user.setRole(userRole);
            }else{
                throw new Exception("Rol 'Teacher' no encontrado en la base de datos");
            }
        }

        /** Save User */
        User savedUser = this.userRepository.save(user);
        savedUser.setPassword(null);

        /** Prepare and send welcome email */
        SendRequest sendRequest = EmailServiceUtil.prepareWelcomeEmail(savedUser);
        emailService.sendMessage(sendRequest, true);

        /** Prepare and send notification-new-user email */
        List<SendRequest> sendRequests = EmailServiceUtil.prepareNotificationEmailToAdmin(findUserByAdministratorRole());
        for (SendRequest sendRequest_1 : sendRequests) {
            emailService.sendMessage(sendRequest_1, true);
        }

        return savedUser;
    }

    private List<User> findUserByAdministratorRole()throws Exception{
        UserRole adminRole = userRoleRepository.findByName("Administrator");
        if(adminRole == null) {
            throw new Exception("No hay rol de administrador en la base de datos");
        }
        List<User> users = userRepository.findByRole(adminRole);
        if (userRepository.findByRole(adminRole) == null) {
            throw new Exception("No hay usuarios con el rol de administrador");
        }
        return users;
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
