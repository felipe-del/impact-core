package com.impact.core.module.mail.enun;

import lombok.Getter;

/**
 * Enum representing the available email templates in the system.
 * <p>
 * This enum defines the different types of email templates that can be used in the application, with each template associated with a specific template name.
 * </p>
 */
@Getter
public enum EMailTemplate {
    GENERIC_EMAIL("generic_email");

    /**
     * The name of the email template.
     */
    private final String templateName;

    /**
     * Constructor to initialize the email template with a name.
     * @param templateName the name of the email template
     */
    EMailTemplate(String templateName) {
        this.templateName = templateName;
    }
}
