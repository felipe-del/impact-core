package com.impact.core.module.mail.service;

import com.impact.core.expection.customException.InternalServerErrorException;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.mail.payload.request.BasicMailRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${impact.mail.sender}")
    private String EMAIL_SENDER;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


    public void sendSimpleEmail(BasicMailRequest mailDetails) {

        if (!validated(mailDetails.getTo())) {
            throw  new IllegalArgumentException("El correo " + mailDetails.getTo() + " no es válido.");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDetails.getTo());
        mailMessage.setSubject(mailDetails.getSubject());
        mailMessage.setText(mailDetails.getMessage());
        mailMessage.setFrom(EMAIL_SENDER);

        mailSender.send(mailMessage);
    }

    public void sendComposedEmail(ComposedMail composedMail) {

        if (!validated(composedMail.getTo())) {
            throw  new IllegalArgumentException("El correo " + composedMail.getTo() + " no es válido.");
        }
        String messageContent = buildMessage(composedMail);
        MimeMessagePreparator preparation = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(EMAIL_SENDER);
            helper.setTo(composedMail.getTo());
            helper.setSubject(composedMail.getSubject());
            helper.setText(messageContent, true);
            addImagesToEmail(helper, composedMail.getImageNames());
        };
        mailSender.send(preparation);
    }

    private Boolean validated(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private String buildMessage(ComposedMail composedMail) throws NotAcceptableStatusException {
        try {
            Context context = new Context();
            composedMail.getMetaData()
                    .forEach(meta -> context.setVariable(meta.getKey(), meta.getValue()));
            return templateEngine.process(composedMail.getTemplate(), context);
        } catch (TemplateInputException e) {
            throw new NotAcceptableStatusException("Error al procesar la plantilla Thymeleaf: " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al construir el mensaje: " + e.getMessage());
        }
    }

    private void addImagesToEmail(MimeMessageHelper helper, List<String> imageNames) {
        if (imageNames == null || imageNames.isEmpty()) {
            return;
        }
        imageNames.forEach(imageName -> {
            try {
                helper.addInline(imageName, new ClassPathResource("images/" + imageName));
            } catch (MessagingException e) {
                System.out.println("Error al adjuntar la imagen: " + e.getMessage());
            }
        });
    }

    // If you want to print the mail properties
    /*
    private final JavaMailSenderImpl mailSenderImpl;
    public void printMailProperties() {
        System.out.println("Host: " + mailSenderImpl.getHost());
        System.out.println("Port: " + mailSenderImpl.getPort());
        System.out.println("Username: " + mailSenderImpl.getUsername());
        System.out.println("Password: " + mailSenderImpl.getPassword());
        System.out.println("Mail Properties: " + mailSenderImpl.getJavaMailProperties());
    }
    */

}
