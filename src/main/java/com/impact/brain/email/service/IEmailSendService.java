package com.impact.brain.email.service;

import com.impact.brain.email.dto.SendRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:56 AM
 */
public interface IEmailSendService {
    ResponseEntity<String> sendMessage(SendRequest sendRequestDTO, boolean async);
}
