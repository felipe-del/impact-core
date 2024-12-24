package com.impact.core.module.mail.factory;

import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.mail.enun.EMailTemplate;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserToken;

import java.util.ArrayList;
import java.util.List;

public class MailFactoryService {
    public static ComposedMail createWelcomeEmail(User user) {
        List<MetaData> metaData = new ArrayList<>();

        metaData.add(new MetaData("name", user.getName()));

        return ComposedMail.builder()
                .to(user.getEmail())
                .subject("Bienvenido a IMPACT")
                .template(EMailTemplate.WELCOME_EMAIL.getTemplateName())
                .metaData(metaData)
                .imageNames(List.of(
                        "UCR_CIMPA_LOGO.png"
                ))
                .build();
    }

    public static ComposedMail createForgotPasswordEmail(UserToken userToken) {
        User user = userToken.getUser();
        String token = userToken.getToken();

        List<MetaData> metaData = new ArrayList<>();

        metaData.add(new MetaData("name", user.getName()));
        metaData.add(new MetaData("token", token));

        return ComposedMail.builder()
                .to(user.getEmail())
                .subject("Restablecer contraseña")
                .template(EMailTemplate.FORGOT_PASSWORD_EMAIL.getTemplateName())
                .metaData(metaData)
                .imageNames(List.of(
                        "UCR_CIMPA_LOGO.png"
                ))
                .build();
    }
}
