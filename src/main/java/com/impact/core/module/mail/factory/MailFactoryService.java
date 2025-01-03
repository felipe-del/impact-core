package com.impact.core.module.mail.factory;

import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.mail.enun.EMailTemplate;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.productRequest.entity.ProductRequest;
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
                .template(EMailTemplate.RESET_PASSWORD_EMAIL.getTemplateName())
                .metaData(metaData)
                .imageNames(List.of(
                        "UCR_CIMPA_LOGO.png"
                ))
                .build();
    }

    public static ComposedMail createRequestSentEmail(ProductRequest productRequest){
        User user = productRequest.getUser();
        Product product = productRequest.getProduct();

        String emailContent = "Se ha enviado una solicitud de producto con los siguientes detalles: \n" +
                "Producto: " + product.getName() + "\n" +
                "Razón: " + productRequest.getReason() + "\n" +
                "Estado: " + productRequest.getStatus().getName() + "\n" +
                "Fecha de creación: " + productRequest.getCreatedAt().toString() + "\n" +
                "Por favor, espere a que se procese su solicitud.";

        List<MetaData> metaData = new ArrayList<>();

        metaData.add(new MetaData("emailTitle", "Solicitud de producto enviada"));
        metaData.add(new MetaData("userName", user.getName()));
        metaData.add(new MetaData("emailContent", emailContent));

        return ComposedMail.builder()
                .to(user.getEmail())
                .subject("Solicitud de producto enviada")
                .template(EMailTemplate.GENERIC_EMAIL.getTemplateName())
                .metaData(metaData)
                .imageNames(List.of(
                        "UCR_CIMPA_LOGO.png"
                ))
                .build();
    }

}
