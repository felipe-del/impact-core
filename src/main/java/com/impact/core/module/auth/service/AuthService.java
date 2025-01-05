package com.impact.core.module.auth.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.UnauthorizedException;
import com.impact.core.module.auth.payload.request.*;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.mapper.MyUserMapper;
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

import java.util.List;
import java.util.stream.Collectors;

@Service("authService")
@RequiredArgsConstructor
public class AuthService {
    // Authentication
    private final AuthenticationManager authenticationManager;
    // User service
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserStateService userStateService;
    private final UserTokenService userTokenService;
    private final MyUserMapper myUserMapper;
    // Mail service
    private final MailService mailService;
    // Security
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

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

    public UserResponse getUserSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == null) {
            throw new UnauthorizedException("No hay usuario autenticado.");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findById(userDetails.getId());
        return myUserMapper.toDTO(user);
    }

    public void logout(LogoutRequest logoutRequest) {
        SecurityContextHolder.clearContext();
        jwtUtils.invalidateJwtToken(logoutRequest.getJwtToken());
    }

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userService.findByEmail(forgotPasswordRequest.getEmail());
        UserToken userToken = userTokenService.generateAndSaveTokenForUser(user);
        ComposedMail forgotPasswordEmail = MailFactory.createForgotPasswordEmail(userToken);
        mailService.sendComposedEmail(forgotPasswordEmail);
    }

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
    }

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

}
