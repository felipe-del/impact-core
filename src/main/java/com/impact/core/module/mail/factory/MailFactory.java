package com.impact.core.module.mail.factory;

import com.impact.core.module.assetRequest.entity.AssetPetition;
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

    public static ComposedMail createChangeUserRoleEmail(String adminName, User user) {
        String emailContent = """
                Se ha cambiado su rol de usuario a: <strong>%s</strong> por el administrador: <strong>%s</strong>.
                """.formatted(user.getRole().getName().toString(), adminName);
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Cambio de rol de usuario"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Cambio de rol de usuario",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createChangeUserStateEmail(String adminName, User user) {
        String emailContent = """
                Se ha cambiado su estado de usuario a: <strong>%s</strong> por el administrador: <strong>%s</strong>.
                """.formatted(user.getState().getName().toString(), adminName);
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Cambio de estado de usuario"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Cambio de estado de usuario",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createWelcomeEmail(User user) {
        String emailContent = "Gracias por registrarte en <strong>IMPACT<strong>. Esperamos que disfrutes de nuestra plataforma.";
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
                Por favor, utiliza el siguiente token para restablecer tu contraseña: <strong> %s </strong>
                """.formatted(userToken.getToken());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Token de restablecimiento"),
                new MetaData("userName", userToken.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(userToken.getUser().getEmail(), "Restablecer contraseña",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    // PRODUCT PETITION EMAILS

    public static ComposedMail createProductPetitionEmail(ProductRequest productRequest) {
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
                new MetaData("emailTitle", "Solicitud de Producto realizada"),
                new MetaData("userName", productRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(productRequest.getUser().getEmail(), "Solicitud de producto realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createAdminReviewProductPetition(ProductRequest productRequest) {
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
                new MetaData("emailTitle", "Revision de solicitud de Producto"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(productRequest.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    // ASSET PETITION EMAILS

    public static ComposedMail createAssetPetitionEmail(AssetPetition assetPetition) {
        String emailContent = """
                Se ha enviado una solicitud de activo con los siguientes detalles: <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, espere a que se procese su solicitud.
                """.formatted(assetPetition.getAsset().getSubcategory().getName(), assetPetition.getReason(),
                assetPetition.getStatus().getName(), assetPetition.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Activo realizada"),
                new MetaData("userName", assetPetition.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(assetPetition.getUser().getEmail(), "Solicitud de activo realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createAdminReviewAssetPetition(AssetPetition assetPetition) {
        String emailContent = """
                Se ha recibido una nueva solicitud de activo que requiere su revisión: <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Correo del solicitante:</strong> %s <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado actual de la solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, revise la solicitud y tome la acción correspondiente (aceptar o rechazar).
                """.formatted(assetPetition.getUser().getName(), assetPetition.getUser().getEmail(),
                assetPetition.getAsset().getSubcategory().getName(), assetPetition.getReason(),
                assetPetition.getStatus().getName(), assetPetition.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Revision de solicitud de Activo"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(assetPetition.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }
}
