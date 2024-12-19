package com.impact.core.module.mail.service;

import com.impact.core.expection.payload.ErrorMessageResponse;
import com.impact.core.expection.payload.MessageResponse;
import com.impact.core.expection.payload.SuccessMessageResponse;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${impact.mail.sender}")
    private String EMAIL_SENDER;

    public MessageResponse sendSimpleEmail(BasicMailRequest mailDetails) {
        if (!validated(mailDetails.getTo())) {
            return new ErrorMessageResponse(500, "El correo " + mailDetails.getTo() + " no es válido", "EMAIL IMPACT MODULE");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDetails.getTo());
        mailMessage.setSubject(mailDetails.getSubject());
        mailMessage.setText(mailDetails.getMessage());
        mailMessage.setFrom(EMAIL_SENDER);

        mailSender.send(mailMessage);
        return new SuccessMessageResponse("Correo emitido a " + mailDetails.getTo());
    }

    public MessageResponse sendComposedEmail(ComposedMail composedMail) {
        System.out.println("Sending email to: " + composedMail.getTo());
        if (!validated(composedMail.getTo())) {
            return new ErrorMessageResponse(500, "El correo " + composedMail.getTo() + " no es válido", "EMAIL IMPACT MODULE");
        }
        String messageContent = buildMessage(composedMail);
        MimeMessagePreparator preparation = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(EMAIL_SENDER);
            helper.setTo(composedMail.getTo());
            helper.setSubject(composedMail.getSubject());
            helper.setText(messageContent, true);
            addImageToEmail(helper);
        };
        mailSender.send(preparation);
        return new SuccessMessageResponse("Correo emitido a " + composedMail.getTo());
    }

    private Boolean validated(String email) {
        // Validate format of email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private String buildMessage(ComposedMail composedMail) throws NotAcceptableStatusException {
        try {
            Context context = new Context();
            // Agregar variables al contexto de Thymeleaf
            for (MetaData meta : composedMail.getMetaData()) {
                context.setVariable(meta.getKey(), meta.getValue());
            }

            // Procesar la plantilla Thymeleaf
            return templateEngine.process(composedMail.getTemplate(), context);
        } catch (TemplateInputException e) {
            throw new NotAcceptableStatusException("Error al procesar la plantilla Thymeleaf: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addImageToEmail(MimeMessageHelper helper) {
        try {
            helper.addInline("UCR_CIMPA_LOGO", new ClassPathResource("images/UCR_CIMPA_LOGO.png"));
        } catch (MessagingException e) {
            System.out.println("Error al agregar la imagen al correo: " + e.getMessage());
        }
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
