package com.impact.core.module.auth.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.UnauthorizedException;
import com.impact.core.module.auth.payload.request.*;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.mapper.MyUserMapper;
import com.impact.core.module.user.payload.request.UserRequest;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.module.user.entity.*;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.enun.EUserState;
import com.impact.core.module.user.service.UserRoleService;
import com.impact.core.module.user.service.UserService;
import com.impact.core.module.user.service.UserStateService;
import com.impact.core.module.user.service.UserTokenService;
import com.impact.core.security.jwt.JwtUtils;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling authentication-related operations.
 * Provides methods for login, registration, password management, user state and role updates.
 */
@Service("authService")
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserStateService userStateService;
    private final UserTokenService userTokenService;
    private final MyUserMapper myUserMapper;
    private final MailService mailService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    /**
     * Authenticates the user and returns a JWT (JSON Web Token) response.
     *
     * @param loginRequest The login request containing email and password.
     * @return A {@link JwtResponse} containing the generated JWT token.
     * @throws UnauthorizedException if the user is not active or suspended.
     */
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findImpactUser(userDetails.getEmail());
        EUserState userState = user.getState().getName();
        if (userState.equals(EUserState.STATE_INACTIVE) || userState.equals(EUserState.STATE_SUSPENDED)) {
            throw new UnauthorizedException("El usuario " + user.getName() + " no está activo.");
        }
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    /**
     * Registers a new user with a default role and state.
     * Sends a welcome email to the newly registered user.
     *
     * @param registerRequest The registration request containing user details.
     * @return A {@link UserResponse} containing the registered user's information.
     * @throws ConflictException if the email is already in use.
     */
    public UserResponse register(RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("El email ya está en uso.");
        }
        String encryptedPassword = encoder.encode(registerRequest.getPassword());
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(encryptedPassword)
                .role(userRoleService.findByName(EUserRole.ROLE_TEACHER)) // Default role
                .state(userStateService.findByName(EUserState.STATE_INACTIVE)) // Default state
                .build();
        User savedUser = userService.save(user);
        ComposedMail welcomeEmail = MailFactory.createWelcomeEmail(savedUser);
        mailService.sendComposedEmail(welcomeEmail);
        return myUserMapper.toDTO(savedUser);
    }

    /**
     * Creates a new user with a randomly generated password.
     * Sends notification emails to both the admin and the new user.
     *
     * @param newUserRequest The request containing new user details.
     * @param createdBy The user who created this new user.
     * @return A {@link UserResponse} containing the new user's information.
     */
    public UserResponse saveUserWithRandomPassword(NewUserRequest newUserRequest, UserDetailsImpl createdBy) {
        String password = this.generateRandomPassword();
        User newUser = User.builder()
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .password(encoder.encode(password)) // Random password
                .role(userRoleService.findById(newUserRequest.getUserRoleId()))
                .state(userStateService.findById(newUserRequest.getUserStatusId()))
                .build();
        User savedUser = userService.save(newUser);
        ComposedMail mailToAdminOrManager = MailFactory.composeAdminNotificationForNewUser(savedUser, createdBy.getUsername(), createdBy.getEmail());
        ComposedMail mailToUser = MailFactory.composeNewUserWelcomeEmail(savedUser, password, createdBy.getEmail());
        mailService.sendComposedEmail(mailToAdminOrManager);
        mailService.sendComposedEmail(mailToUser);
        return myUserMapper.toDTO(savedUser);
    }

    /**
     * Retrieves the currently authenticated user's session information.
     *
     * @return A {@link UserResponse} containing the session user's information.
     * @throws UnauthorizedException if no user is authenticated.
     */
    public UserResponse getUserSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == null) {
            throw new UnauthorizedException("No hay usuario autenticado.");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findById(userDetails.getId());
        return myUserMapper.toDTO(user);
    }

    /**
     * Logs out the current user and invalidates the JWT token.
     *
     * @param logoutRequest The logout request containing the JWT token.
     */
    public void logout(LogoutRequest logoutRequest) {
        SecurityContextHolder.clearContext();
        jwtUtils.invalidateJwtToken(logoutRequest.getJwtToken());
    }

    /**
     * Initiates the password reset process by sending a reset token to the user's email.
     *
     * @param forgotPasswordRequest The request containing the user's email.
     */
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userService.findByEmail(forgotPasswordRequest.getEmail());
        UserToken userToken = userTokenService.generateAndSaveTokenForUser(user);
        ComposedMail forgotPasswordEmail = MailFactory.createForgotPasswordEmail(userToken);
        mailService.sendComposedEmail(forgotPasswordEmail);
    }

    /**
     * Resets the user's password using the provided token and new password.
     *
     * @param resetPasswordRequest The request containing the reset token and new password.
     * @throws UnauthorizedException if the token is invalid or expired.
     */
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();
        String password = resetPasswordRequest.getPassword();
        String encryptedPassword = encoder.encode(password);
        if (!userTokenService.validateToken(token)) {
            throw new UnauthorizedException("El token ha expirado.");
        }
        UserToken userToken = userTokenService.findByToken(token);
        User user = userToken.getUser();
        user.setPassword(encryptedPassword);
        userService.save(user);
        userTokenService.delete(userToken);
        ComposedMail resetPasswordEmail = MailFactory.createPasswordResetEmail(user);
        mailService.sendComposedEmail(resetPasswordEmail);
    }

    /**
     * Changes the current user's password.
     *
     * @param changePasswordRequest The request containing the old password, new password, and confirmation.
     * @throws UnauthorizedException if the old password does not match or the new passwords do not match.
     * @throws ConflictException if the new password is the same as the old one.
     */
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findById(userDetails.getId());
        if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedException("La contraseña actual no coincide.");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new ConflictException("La contraseña nueva no coincide.");
        }
        String encryptedPassword = encoder.encode(changePasswordRequest.getNewPassword());
        if (encoder.matches(changePasswordRequest.getOldPassword(), encryptedPassword)) {
            throw new ConflictException("La nueva contraseña no puede ser igual a la anterior.");
        }
        user.setPassword(encryptedPassword);
        userService.save(user);
        ComposedMail changePasswordEmail = MailFactory.createPasswordChangedEmail(user);
        mailService.sendComposedEmail(changePasswordEmail);
    }

    /**
     * Changes the state of a user.
     *
     * @param id The ID of the user whose state is to be changed.
     * @param changeUserStateRequest The request containing the new user state.
     * @param userDetailsSession The details of the user performing the change.
     * @return A {@link UserResponse} containing the updated user information.
     * @throws ConflictException if the user already has the specified state.
     */
    public UserResponse changeUserState(int id, ChangeUserStateRequest changeUserStateRequest, UserDetailsImpl userDetailsSession) {
        User user = userService.findById(id);
        UserState userState = userStateService.findById(changeUserStateRequest.getStateId());
        if(user.getState().getName().equals(userState.getName())){
            throw new ConflictException("El usuario ya tiene el estado: '" + userState.getName().toString() + "'.");
        }
        user.setState(userState);
        User savedUser = userService.save(user);
        // Email notification
        ComposedMail composedMail = MailFactory.createChangeUserStateEmail(userDetailsSession.getUsername(), savedUser);
        mailService.sendComposedEmail(composedMail);
        return myUserMapper.toDTO(savedUser);
    }

    /**
     * Changes the role of a user.
     *
     * @param id The ID of the user whose role is to be changed.
     * @param changeUserRoleRequest The request containing the new user role.
     * @param userDetailsSession The details of the user performing the change.
     * @return A {@link UserResponse} containing the updated user information.
     * @throws ConflictException if the user already has the specified role.
     */
    public UserResponse changeUserRole(int id, ChangeUserRoleRequest changeUserRoleRequest, UserDetailsImpl userDetailsSession) {
        User user = userService.findById(id);
        UserRole userRole = userRoleService.findById(changeUserRoleRequest.getRoleId());
        if(user.getRole().getName().equals(userRole.getName())){
            throw new ConflictException("El usuario ya tiene el rol: '" + userRole.getName().toString() + "'.");
        }
        user.setRole(userRole);
        User savedUser = userService.save(user);
        // Email notification
        ComposedMail composedMail = MailFactory.createChangeUserRoleEmail(userDetailsSession.getUsername(), savedUser);
        mailService.sendComposedEmail(composedMail);
        return myUserMapper.toDTO(savedUser);
    }

    // PRIVATE METHODS


    /**
     * Generates a random password consisting of letters, numbers, and special characters.
     *
     * @return A randomly generated password.
     */
    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?/";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

}
