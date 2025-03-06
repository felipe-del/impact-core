package com.impact.core.module.mail.factory;

import com.impact.core.module.assetRequest.entity.AssetRequest;
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

    public static ComposedMail createPasswordResetEmail(User user) {
        String emailContent = "Tu contraseña ha sido restablecida con éxito. Si no has sido tú, por favor, contacta con el soporte.";
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Contraseña restablecida"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Contraseña restablecida",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createPasswordChangedEmail(User user) {
        String emailContent = "Tu contraseña ha sido cambiada con éxito. Si no has sido tú, por favor, contacta con el soporte.";
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Contraseña cambiada"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Contraseña cambiada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    // PRODUCT Request EMAILS

    public static ComposedMail createProductRequestEmail(ProductRequest productRequest) {
        String emailContent = """
                Se ha enviado una solicitud de producto con los siguientes detalles: <br>
                <strong>Producto (Categoría):</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, espere a que se procese su solicitud.
                """.formatted(productRequest.getProduct().getCategory().getName(), productRequest.getReason(),
                productRequest.getStatus().getName(), productRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Producto realizada"),
                new MetaData("userName", productRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(productRequest.getUser().getEmail(), "Solicitud de producto realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createAdminReviewProductRequest(ProductRequest productRequest) {
        String emailContent = """
                Se ha recibido una nueva solicitud de producto que requiere su revisión: <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Correo del solicitante:</strong> %s <br>
                <strong>Producto (Categoría):</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado actual de la solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, revise la solicitud y tome la acción correspondiente (aceptar o rechazar).
                """.formatted(productRequest.getUser().getName(), productRequest.getUser().getEmail(),
                productRequest.getProduct().getCategory().getName(), productRequest.getReason(),
                productRequest.getStatus().getName(), productRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Revision de solicitud de Producto"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(productRequest.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    // ASSET Request EMAILS

    public static ComposedMail createAssetRequestEmail(AssetRequest assetRequest) {
        String emailContent = """
                Se ha enviado una solicitud de activo con los siguientes detalles: <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, espere a que se procese su solicitud.
                """.formatted(assetRequest.getAsset().getSubcategory().getName(), assetRequest.getReason(),
                assetRequest.getStatus().getName(), assetRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Activo realizada"),
                new MetaData("userName", assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(), "Solicitud de activo realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    public static ComposedMail createAdminReviewAssetRequest(AssetRequest assetRequest) {
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
                """.formatted(assetRequest.getUser().getName(), assetRequest.getUser().getEmail(),
                assetRequest.getAsset().getSubcategory().getName(), assetRequest.getReason(),
                assetRequest.getStatus().getName(), assetRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Revision de solicitud de Activo"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }

    // to new user with password random
    public static ComposedMail composeNewUserWelcomeEmail(User user, String password, String createdBy) {
        String emailContent = """
                El administrador o gestor: <strong>%s</strong> ha creado una cuenta para ti en IMPACT. <br><br>
                Tus credenciales de inicio de sesión son: <br>
                <strong>Correo:</strong> %s <br>
                <strong>Contraseña:</strong> %s <br>
                <br>
                Por favor, cambia tu contraseña en tu primer inicio de sesión.
                """.formatted(createdBy, user.getEmail(), password);
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Cuenta creada en IMPACT"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Cuenta creada en IMPACT",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }
    // to send a admin that create a new user
    public static ComposedMail composeAdminNotificationForNewUser(User user, String createdBy_NAME, String createdBy_EMAIL) {
        String emailContent = """
                    Has creado una cuenta para el usuario: <strong>%s</strong> en IMPACT. <br>
                    """.formatted(user.getName());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Cuenta creada en IMPACT"),
                new MetaData("userName", createdBy_NAME),
                new MetaData("emailContent", emailContent));
        return createEmail(createdBy_EMAIL, "Cuenta creada en IMPACT",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE));
    }
    // to send notification about asset expiration date request
    public static ComposedMail composeUserNotificationExpirationDateAssetRequest(AssetRequest assetRequest){
        String emailContent = """
                El préstamo del activo que solicitó está próximo a vencer <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Categoría del Activo:</strong> %s <br>
                <strong>Placa:</strong> %s <br>
                <strong>Fecha de vencimiento:</strong> %s <br>
                <br>
                Por favor, renueve la solicitud del activo o cancele
                """.formatted(assetRequest.getUser().getName(),
                assetRequest.getAsset().getSubcategory().getName(),assetRequest.getAsset().getPlateNumber(),
                assetRequest.getExpirationDate());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación del vencimiento de activo prestado."),
                new MetaData("userName",assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(),"Notificación de vencimiento de préstamo",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE));
    }

}
