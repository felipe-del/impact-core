package com.impact.core.module.mail.service;

import com.impact.core.expection.customException.InternalServerErrorException;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.request.BasicMailRequest;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service class for sending emails, including basic and composed emails with dynamic templates.
 * <p>
 * This service is responsible for sending various types of emails, such as simple text emails and
 * dynamic emails generated from templates. It uses Spring's {@link JavaMailSender} for sending
 * emails and Thymeleaf for processing email templates.
 * </p>
 */
@Slf4j
@Service("mailService")
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final UserService userService;

    @Value("${impact.mail.sender}")
    private String EMAIL_SENDER;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    // SENDING EMAILS METHODS

    /**
     * Sends a simple email to a single recipient.
     * <p>
     * This method uses Spring's {@link SimpleMailMessage} to send a basic email with a subject
     * and message. The email is sent asynchronously to avoid blocking the main application flow.
     * </p>
     *
     * @param mailDetails The {@link BasicMailRequest} containing the email details (recipient, subject, and message).
     */
    @Async
    public void sendBasicEmail(BasicMailRequest mailDetails) {
        if (!validated(mailDetails.getTo())) {
            throw  new IllegalArgumentException("El correo " + mailDetails.getTo() + " no es válido.");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDetails.getTo());
        mailMessage.setSubject(mailDetails.getSubject());
        mailMessage.setText(mailDetails.getMessage());
        mailMessage.setFrom(EMAIL_SENDER);

        mailSender.send(mailMessage);
        logEmailSent(mailDetails.getTo());
    }

    /**
     * Sends a composed email with dynamic content to a single recipient.
     * <p>
     * This method constructs a dynamic email message based on a template using Thymeleaf.
     * It also supports adding inline images to the email.
     * </p>
     *
     * @param composedMail The {@link ComposedMail} containing the recipient, subject, template, metadata, and images.
     */
    @Async
    public void sendComposedEmail(ComposedMail composedMail) {
        String emailRecipient = composedMail.getTo();
        if (!validated(emailRecipient)) {
            logInvalidEmail(emailRecipient);
            throw  new IllegalArgumentException("El correo " + emailRecipient + " no es válido.");
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
        logEmailSent(emailRecipient);
    }

    /**
     * Sends a composed email to all users with the "ADMIN" role.
     * <p>
     * This method retrieves all users with the "ADMIN" role and sends them the composed email.
     * It constructs the email based on the provided {@link ComposedMail} template.
     * </p>
     *
     * @param composedMail The {@link ComposedMail} containing the email details to be sent to all admins.
     */
    @Async
    public void sendComposedEmailToAllAdmins(ComposedMail composedMail) {
        List<User> admins = userService.getAllAdmins();
        for (User admin : admins) {
            String emailRecipient = admin.getEmail();
            if (!validated(emailRecipient)) {
                logInvalidEmail(emailRecipient);
            }
            String messageContent = buildMessage(composedMail);
            MimeMessagePreparator preparation = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(EMAIL_SENDER);
                helper.setTo(emailRecipient);
                helper.setSubject(composedMail.getSubject());
                helper.setText(messageContent, true);
                addImagesToEmail(helper, composedMail.getImageNames());
            };
            mailSender.send(preparation);
            logEmailSent(emailRecipient);
        }
    }

    // ==============================
    // PRIVATE HELPER METHODS
    // ==============================

    /**
     * Validates the given email address using a regular expression pattern.
     *
     * @param email The email address to validate.
     * @return {@code true} if the email address matches the pattern, {@code false} otherwise.
     */
    private Boolean validated(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Builds the message content for a composed email by processing the given template with metadata.
     * <p>
     * This method uses Thymeleaf to process the email template and inject dynamic content from the
     * metadata into the template.
     * </p>
     *
     * @param composedMail The {@link ComposedMail} containing template and metadata.
     * @return The processed email message as a {@link String}.
     * @throws NotAcceptableStatusException If there's an error in processing the template.
     */
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

    /**
     * Adds inline images to the email message.
     * <p>
     * This method attaches images to the email, which can be referenced within the email template.
     * </p>
     *
     * @param helper The {@link MimeMessageHelper} used to attach images.
     * @param imageNames A list of image filenames to attach to the email.
     */
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

    /**
     * Logs a successful email send event.
     * <p>
     * This method logs the details of the email recipient for monitoring purposes.
     * </p>
     *
     * @param emailRecipient The email address of the recipient.
     */
    private void logEmailSent(String emailRecipient) {
        log.info("The IMPACT email module just sent one to {}", emailRecipient);
    }

    /**
     * Logs an invalid email address event.
     * <p>
     * This method logs when an invalid email address is encountered.
     * </p>
     *
     * @param email The invalid email address.
     */
    private void logInvalidEmail(String email) {
        log.error("The email {} is not valid", email);
    }
}
