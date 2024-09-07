package com.impact.brain.auth.service;

import com.impact.brain.auth.dto.ChangePasswordRequest;
import com.impact.brain.auth.dto.VerifyRequest;
import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.intity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:00 PM
 */
public interface IAuthService {

    UserDTO authenticate(String email, String password, HttpServletRequest request);
    UserDTO getCurrentUserSession();
    void forgotPassword(String email);
    ResponseEntity<String> verifyCode(VerifyRequest verifyRequest);
    User changePassword(ChangePasswordRequest changePasswordRequest);
}
