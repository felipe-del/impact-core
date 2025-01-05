package com.impact.core.module.mail.controller;

import com.impact.core.module.mail.enun.EMailTemplate;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.mail.payload.request.BasicMailRequest;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.entity.User;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test/mail")
@RequiredArgsConstructor
public class TestMailController {
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> sendSimpleMessage(
            @Valid @RequestBody BasicMailRequest loginRequest) {
        mailService.sendBasicEmail(loginRequest);
        Map<String, String> response = Map.of(
                "emisor", "IMPACT SYSTEM",
                "receptor", loginRequest.getTo());
        return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                .message("Correo enviado exitosamente")
                .data(response)
                .build());
    }

    @PostMapping("/sendMail")
    public ResponseEntity<ApiResponse<String>> sendMail() {
        mailService.sendComposedEmail(MailFactory.createWelcomeEmail(
                User.builder()
                        .email("isaacfelibrenes1904@gmail.com")
                        .name("Isaac Felipe")
                        .build()
        ));
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .message("Correo enviado exitosamente")
                .data("Receptor del correo: " + "Isaac Felipe")
                .build());
    }
}
