package com.impact.brain.user.service;

import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.entity.User;

import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:26 AM
 */
public interface IUserService {
    List<UserDTO> findAll();

    User saveUser(User user) throws Exception;

    User findByEmail(String email);
    User findByUsername(String username);

    User findById(int id);
    User changePassword(int userId, String newPassword);
    UserDTO mapToUserDTO(User user);
}
