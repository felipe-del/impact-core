package com.impact.core.module.mail.service;

public enum EMailTemplate {
    WELCOME_EMAIL("new_user_welcome_email_template");

    private final String templateName;

    EMailTemplate(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
