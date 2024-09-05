package com.impact.brain.configuration.emailConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 11:05 AM
 */

/**
 * Configuration class for setting up email sending capabilities.
 * <p>
 * This class provides the necessary configuration for sending emails using the JavaMailSender interface.
 * It loads email properties from a properties file and sets up the mail sender with appropriate settings.
 * </p>

 */
@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    /**
     * The username for the email account used to send emails.
     */
    @Value("${email.username}")
    private String username;

    /**
     * The password for the email account used to send emails.
     */
    @Value("${email.password}")
    private String password;

    /**
     * Creates a {@link Properties} object with mail server properties.
     * <p>
     * This method sets up the necessary properties for connecting to the SMTP server, including authentication,
     * TLS, host, and port.
     * </p>
     *
     * @return a Properties object containing mail server configuration.
     */
    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    /**
     * Creates a {@link JavaMailSender} bean with configured properties.
     * <p>
     * This method initializes a {@link JavaMailSenderImpl} object with the properties for the SMTP server,
     * and sets the username and password for the email account.
     * </p>
     *
     * @return a JavaMailSender instance configured for sending emails.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(getMailProperties());
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }

    /**
     * Creates a {@link ResourceLoader} bean for loading resources.
     * <p>
     * This method provides a default implementation of {@link ResourceLoader} for loading resources from the
     * classpath and other locations.
     * </p>
     *
     * @return a ResourceLoader instance.
     */
    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }
}
