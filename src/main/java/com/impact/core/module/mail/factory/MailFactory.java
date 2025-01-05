package com.impact.core.module.mail.factory;

import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.mail.enun.EMailTemplate;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserToken;

import java.util.List;

public class MailFactory {

    private static final String IMPACT_LOGO_IMAGE = "IMPACT_BLACK_LOGO.png";

    private static ComposedMail createEmail(String recipient, String subject, EMailTemplate template,
                                            List<MetaData> metaData, List<String> imageNames) {
        return ComposedMail.builder()
                .to(recipient)
                .subject(subject)
                .template(template.getTemplateName())
                .metaData(metaData)
                .imageNames(imageNames)
                .build();
    }

    public static ComposedMail createWelcomeEmail(User user) {
        String emailContent = "Gracias por registrarte en IMPACT. Esperamos que disfrutes de nuestra plataforma.";

        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Bienvenido a IMPACT"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(user.getEmail(), "Bienvenido a IMPACT",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createForgotPasswordEmail(UserToken userToken) {
        String emailContent = """
                Hemos recibido una solicitud para restablecer la contraseña de tu cuenta. <br>
                Por favor, utiliza el siguiente token para restablecer tu contraseña: <strong>%s</strong>
                """.formatted(userToken.getToken());

        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Token de restablecimiento"),
                new MetaData("userName", userToken.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(userToken.getUser().getEmail(), "Restablecer contraseña",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createProductRequestEmail(ProductRequest productRequest) {
        String emailContent = """
                Se ha enviado una solicitud de producto con los siguientes detalles: <br>
                <strong>Producto:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, espere a que se procese su solicitud.
                """.formatted(productRequest.getProduct().getName(), productRequest.getReason(),
                productRequest.getStatus().getName(), productRequest.getCreatedAt().toString());

        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de producto realizada"),
                new MetaData("userName", productRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(productRequest.getUser().getEmail(), "Solicitud de producto realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }
    public static ComposedMail createAdminReviewRequest(ProductRequest productRequest) {
        String emailContent = """
                Se ha recibido una nueva solicitud de producto que requiere su revisión: <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Correo del solicitante:</strong> %s <br>
                <strong>Producto:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado actual de la solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, revise la solicitud y tome la acción correspondiente (aceptar o rechazar).
                """.formatted(productRequest.getUser().getName(), productRequest.getUser().getEmail(),
                productRequest.getProduct().getName(), productRequest.getReason(),
                productRequest.getStatus().getName(), productRequest.getCreatedAt().toString());

        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Revision de solicitud"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));

        return createEmail(productRequest.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }
}
