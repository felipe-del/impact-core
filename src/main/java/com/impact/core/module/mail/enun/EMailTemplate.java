package com.impact.core.module.mail.enun;

import lombok.Getter;

@Getter
public enum EMailTemplate {
    WELCOME_EMAIL("new_user_welcome_email_template"),
    RESET_PASSWORD_EMAIL("reset_password_email_template"),
    GENERIC_EMAIL("generic_email_template");

    private final String templateName;

    EMailTemplate(String templateName) {
        this.templateName = templateName;
    }

}
