package com.impact.core.module.auth.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.UnauthorizedException;
import com.impact.core.module.auditLog.listener.AuditLogListener;
import com.impact.core.module.auth.payload.request.LoginRequest;
import com.impact.core.module.auth.payload.request.RegisterRequest;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.mail.factory.MailFactoryService;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.dto.UserDTO;
import com.impact.core.module.user.entity.*;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.enun.EUserState;
import com.impact.core.module.user.service.UserRoleService;
import com.impact.core.module.user.service.UserService;
import com.impact.core.module.user.service.UserStateService;
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
        User user = userService.findByEmail(userDetails.getEmail());;

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

    public UserDTO register(RegisterRequest registerRequest) {

        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("El email ya está en uso.");
        }

        User user = User.builder()
                .id(0)
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .role(getUserRole(registerRequest.getRole()))
                .state(getUserState(registerRequest.getState()))
                .build();

        User savedUser = userService.save(user);

        ComposedMail welcomeEmail = MailFactoryService.createWelcomeEmail(savedUser);
        mailService.sendComposedEmail(welcomeEmail);

        return userService.toDTO(savedUser);
    }

    private UserRole getUserRole(String role) {
        return switch (role.toLowerCase()) {
            case "admin" -> userRoleService.findByName(EUserRole.ROLE_ADMINISTRATOR);
            case "manager" -> userRoleService.findByName(EUserRole.ROLE_MANAGER);
            default -> userRoleService.findByName(EUserRole.ROLE_TEACHER);
        };
    }

    private UserState getUserState(String state) {
        return switch (state.toLowerCase()) {
            case "active" -> userStateService.findByName(EUserState.STATE_ACTIVE);
            case "suspend" -> userStateService.findByName(EUserState.STATE_SUSPENDED);
            default -> userStateService.findByName(EUserState.STATE_INACTIVE);
        };
    }

    public UserDTO getUserSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() == null) {
            throw new UnauthorizedException("No hay usuario autenticado.");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findById(userDetails.getId());

        return userService.toDTO(user);
    }

    public void logout() {
    }


}
