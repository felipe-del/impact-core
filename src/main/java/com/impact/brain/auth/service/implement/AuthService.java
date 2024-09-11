package com.impact.brain.auth.service.implement;

import com.impact.brain.auth.dto.ChangePasswordRequest;
import com.impact.brain.auth.dto.VerifyRequest;
import com.impact.brain.auth.service.IAuthService;
import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.email.service.impl.EmailSendService;
import com.impact.brain.email.util.EmailServiceUtil;
import com.impact.brain.security.UserDetailsImp;
import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.impl.UserService;
import com.impact.brain.userToken.entity.UserToken;
import com.impact.brain.userToken.service.impl.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 1:00 PM
 */
@Service
public class AuthService implements IAuthService {
    private final EmailSendService emailService;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthService(EmailSendService emailService, UserService userService, TokenService tokenService) {
        this.emailService = emailService;
        this.userService = userService;
        this.tokenService = tokenService;
    }
    @Override
    public UserDTO authenticate(String email, String password, HttpServletRequest request) {
        try {
            request.login(email, password);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImp userDetails = (UserDetailsImp) auth.getPrincipal();
            User authenticatedUser = userDetails.getUser();
            System.out.println(authenticatedUser);
            // Si hay relaciones en User, asegúrate de que están inicializadas
            if (authenticatedUser.getRole() != null) {
                Hibernate.initialize(authenticatedUser.getRole()); // Inicializa el proxy si es necesario
            }

            authenticatedUser.setPassword(null); // No devolver la contraseña
            return userService.mapToUserDTO(authenticatedUser);
        } catch (ServletException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDTO getCurrentUserSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetailsImp)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User userSession = userDetails.getUser();
        userSession.setPassword(null); // Optionally clear the password before returning

        return userService.mapToUserDTO(userSession);
    }
    @Override
    public void forgotPassword(String email) {
        try {
            User user = userService.findByEmail(email);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this email does not exist");
            }

            // Generar un token seguro para el restablecimiento de contraseña
            String resetToken = generateResetToken();

            // Calcular la fecha de expiración del token (por ejemplo, 24 horas después de ahora)
            Instant expiryDate = Instant.now().plusSeconds(24 * 60 * 60);

            // Guardar el token en la base de datos
            UserToken token = new UserToken(user, resetToken, expiryDate, Instant.now());
            tokenService.saveToken(token);

            // Preparar el contexto para la plantilla de email (Thymeleaf)
            SendRequest sendRequest = EmailServiceUtil.preparePasswordResetEmail(user, resetToken);

            // Enviar el correo de restablecimiento de contraseña
            emailService.sendMessage(sendRequest, false);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing forgot password request", e);
        }
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int TOKEN_LENGTH = 6;
    private static final Random RANDOM = new Random();

    public static String generateResetToken() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(index));
            if (i < TOKEN_LENGTH - 1) {
                token.append(' ');
            }
        }
        return token.toString();
    }
    @Override
    public ResponseEntity<String> verifyCode(VerifyRequest verifyRequest) {
        try {
            // Verificar el token recibido
            UserToken token = tokenService.findByToken(verifyRequest.getCode());

            System.out.printf("VER: "+token.toString());

            if (token == null || token.getExpiryDate().isBefore(Instant.now())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired token");
            }

            // Obtener el usuario asociado al token
            Optional<User> userOptional = userService.findById(token.getUser().getId());

            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for token");
            }

            // Eliminar el token después de ser verificado
            //tokenService.deleteToken(token.getId());

            // La verificación fue exitosa, devuelve una respuesta exitosa
            return ResponseEntity.ok("Code verified successfully");
        } catch (Exception e) {
            // Manejar cualquier error y devolver una respuesta adecuada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to verify code: " + e.getMessage());
        }
    }
    @Override
    public User changePassword(ChangePasswordRequest changePasswordRequest) {

        String code = changePasswordRequest.getCode();
        String password = changePasswordRequest.getPassword();
        UserToken token = tokenService.findByToken(code);
        System.out.println("El token es "+ token.toString());
        Optional<User> user = userService.findById(token.getUser().getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for token");
        }

        return userService.changePassword(user.get().getId(), password);
    }

}

