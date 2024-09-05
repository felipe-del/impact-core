package com.impact.brain.email.controller;

import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.email.service.impl.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:54 AM
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailSendService emailSendService;

    @Autowired
    public EmailController(EmailSendService emailSendService) {
        this.emailSendService = emailSendService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody SendRequest sendRequest) {
        return emailSendService.sendMessage(sendRequest, false);
    }

}
