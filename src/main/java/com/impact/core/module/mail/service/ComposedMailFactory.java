package com.impact.core.module.mail.service;

import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.user.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ComposedMailFactory {
    public static ComposedMail createWelcomeEmail(User user) {
        List<MetaData> metaData = new ArrayList<>();

        metaData.add(new MetaData("name", user.getName()));

        return ComposedMail.builder()
                .to(user.getEmail())
                .subject("Bienvenido a IMPACT")
                .template(EMailTemplate.WELCOME_EMAIL.getTemplateName())
                .metaData(metaData)
                .build();
    }
}
