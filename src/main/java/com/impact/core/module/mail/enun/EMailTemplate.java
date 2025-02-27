package com.impact.core.module.mail.enun;

import lombok.Getter;

@Getter
public enum EMailTemplate {
    GENERIC_EMAIL("generic_email");

    private final String templateName;

    EMailTemplate(String templateName) {
        this.templateName = templateName;
    }
}
