package com.impact.core.module.auth.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.module.auth.payload.request.LoginRequest;
import com.impact.core.module.auth.payload.request.RegisterRequest;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.expection.payload.SuccessMessageResponse;
import com.impact.core.module.user.entity.ERole;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.service.UserRoleService;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.jwt.JwtUtils;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public User register(RegisterRequest registerRequest) {

        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("El email ya está en uso.");
        }

        User user = User.builder()
                .id(0)
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .role(getUserRole(registerRequest.getRole()))
                .state(null)
                .build();

        return userService.save(user);
    }

    private UserRole getUserRole(String role) {
        return switch (role.toLowerCase()) {
            case "admin" -> userRoleService.findByName(ERole.ROLE_ADMINISTRATOR);
            case "manager" -> userRoleService.findByName(ERole.ROLE_MANAGER);
            default -> userRoleService.findByName(ERole.ROLE_TEACHER);
        };
    }

}
