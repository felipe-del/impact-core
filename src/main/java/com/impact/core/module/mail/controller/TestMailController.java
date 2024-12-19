package com.impact.core.module.mail.controller;

import com.impact.core.expection.payload.MessageResponse;
import com.impact.core.expection.payload.SuccessMessageResponse;
import com.impact.core.module.mail.payload.request.BasicMailRequest;
import com.impact.core.module.mail.service.ComposedMailFactory;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class TestMailController {
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<SuccessMessageResponse> sendSimpleMessage(@Valid @RequestBody BasicMailRequest loginRequest) {
        mailService.sendSimpleEmail(loginRequest);
        return ResponseEntity.ok(new SuccessMessageResponse("Email enviado"));
    }

    @PostMapping("/testSendMail")
    public ResponseEntity<MessageResponse> testSendMail() {
        MessageResponse msg = mailService.sendComposedEmail(ComposedMailFactory.createWelcomeEmail(
                User.builder()
                        .email("isaacfelibrenes1904@gmail.com")
                        .name("Isaac Felipe")
                        .build()
        ));
        return ResponseEntity.ok(msg);
    }
}
