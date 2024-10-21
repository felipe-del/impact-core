package com.impact.brain.email.miniTest;
import com.impact.brain.email.dto.MetaData;
import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.email.service.impl.EmailSendService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 10/20/2024 - 11:53 PM
 */

@Component
public class EmailInitializer {

    private final EmailSendService emailSendService;

    @Autowired
    public EmailInitializer(EmailSendService emailSendService) {
        this.emailSendService = emailSendService;
    }

    //@PostConstruct
    public void init() {
        SendRequest sendRequest = new SendRequest();
        sendRequest.setTo("isaacfelibrenes1904@gmail.com");
        sendRequest.setSubject("Bienvenido a IMPACT");
        sendRequest.setTemplate("welcome-email");

        // Setting the metadata (you can add more key-value pairs as needed)
        MetaData metaData = new MetaData();
        metaData.setKey("name");
        metaData.setValue("Isaac Brenes");
        sendRequest.setMetaData(List.of(metaData));

        System.out.println("Sending welcome email to: " + sendRequest.getTo());

        // Sending the email
        emailSendService.sendMessage(sendRequest, false);
    }
}
