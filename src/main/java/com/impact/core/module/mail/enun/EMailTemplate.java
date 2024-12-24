package com.impact.core.module.mail.enun;

public enum EMailTemplate {
    WELCOME_EMAIL("new_user_welcome_email_template"),
    FORGOT_PASSWORD_EMAIL("forgot_password_email_template");

    private final String templateName;

    EMailTemplate(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
