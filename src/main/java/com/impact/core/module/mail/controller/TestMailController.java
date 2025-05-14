package com.impact.core.module.mail.controller;

import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.request.BasicMailRequest;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller class for testing email sending functionality.
 * <p>
 * This controller provides endpoints for sending simple and composed emails for testing purposes.
 * It also includes a method for sending a composed welcome email to users with specific roles.
 * </p>
 */
@RestController
@RequestMapping("/api/test/mail")
@RequiredArgsConstructor
public class TestMailController {
    private final MailService mailService;
    private final UserService userService;

    /**
     * Sends a simple email message.
     * <p>
     * This endpoint sends a basic email to the recipient specified in the {@link BasicMailRequest}.
     * The email is sent using the {@link MailService#sendBasicEmail} method.
     * </p>
     *
     * @param loginRequest The {@link BasicMailRequest} containing the email details (recipient, subject, and message).
     * @return A {@link ResponseEntity} with a message indicating the success of the operation, and the recipient's email.
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper<Map<String, String>>> sendSimpleMessage(
            @Valid @RequestBody BasicMailRequest loginRequest) {
        mailService.sendBasicEmail(loginRequest);
        Map<String, String> response = Map.of(
                "emisor", "IMPACT SYSTEM",
                "receptor", loginRequest.getTo());
        return ResponseEntity.ok(ResponseWrapper.<Map<String, String>>builder()
                .message("Correo enviado exitosamente")
                .data(response)
                .build());
    }

    /**
     * Sends a composed email to a test recipient.
     * <p>
     * This endpoint sends a composed welcome email to a test user using the {@link MailFactory#createWelcomeEmail}.
     * The email is sent using the {@link MailService#sendComposedEmail} method.
     * </p>
     *
     * @return A {@link ResponseEntity} with a success message and the recipient's name.
     */
    @PostMapping("/sendMail")
    public ResponseEntity<ResponseWrapper<String>> sendMail() {
        mailService.sendComposedEmail(MailFactory.createWelcomeEmail(
                User.builder()
                        .email("isaacfelibrenes1904@gmail.com")
                        .name("Isaac Felipe")
                        .build()
        ));
        return ResponseEntity.ok(ResponseWrapper.<String>builder()
                .message("Correo enviado exitosamente")
                .data("Receptor del correo: " + "Isaac Felipe")
                .build());
    }

    /**
     * Sends a composed email to a user with specific roles.
     * <p>
     * This endpoint sends a composed welcome email to the authenticated user with a role of either "ADMINISTRATOR"
     * or "MANAGER". It uses the {@link MailFactory#createWelcomeEmail} to generate the email.
     * The email is sent using the {@link MailService#sendComposedEmail} method.
     * </p>
     *
     * @param userDetails The authenticated user details obtained from the security context.
     * @return A {@link ResponseEntity} with a success message and the recipient's email.
     */
    @PostMapping("/sendComposedMail")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<String>> sendComposedMail(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        ComposedMail componentModel = MailFactory.createWelcomeEmail(user);
        mailService.sendComposedEmail(componentModel);

        return ResponseEntity.ok(ResponseWrapper.<String>builder()
                .message("Correo enviado exitosamente")
                .data("Receptor del correo: " + user.getEmail())
                .build());
    }
}
