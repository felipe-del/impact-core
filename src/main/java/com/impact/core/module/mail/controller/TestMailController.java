package com.impact.core.module.mail.controller;

import ch.qos.logback.core.model.ComponentModel;
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

@RestController
@RequestMapping("/api/test/mail")
@RequiredArgsConstructor
public class TestMailController {
    private final MailService mailService;
    private final UserService userService;

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
