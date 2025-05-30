package com.impact.core.module.mail.factory;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.payload.MetaData;
import com.impact.core.module.mail.enun.EMailTemplate;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.entity.UserToken;

import java.util.List;

/**
 * Factory class for creating different types of composed emails.
 * <p>
 * This class provides methods to construct {@link ComposedMail} objects for sending emails with specific templates
 * and dynamic metadata. It handles various predefined templates, images, and metadata required for constructing the
 * email content.
 * </p>
 */
public class MailFactory {

    private static final String IMPACT_LOGO_IMAGE = "IMPACT_BLACK_LOGO.png";
    private static final String IMPACT_LOGO_IMAGE_WHITE = "NEW_IMPACT_WHITE_LOGO.jpg";
    private static final String UCR_CIMPA_BANNER = "UCR_CIMPA_WHITE_BACKGROUND_BANNER.png";

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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createWelcomeEmail(User user) {
        String emailContent = "Gracias por registrarte en <strong>IMPACT<strong>.<br>Esperamos que disfrutes de nuestra plataforma.";
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Bienvenido a IMPACT"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Bienvenido a IMPACT",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createPasswordResetEmail(User user) {
        String emailContent = "Tu contraseña ha sido restablecida con éxito. Si no has sido tú, por favor, contacta con el soporte.";
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Contraseña restablecida"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Contraseña restablecida",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createPasswordChangedEmail(User user) {
        String emailContent = "Tu contraseña ha sido cambiada con éxito. Si no has sido tú, por favor, contacta con el soporte.";
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Contraseña cambiada"),
                new MetaData("userName", user.getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(user.getEmail(), "Contraseña cambiada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    // ASSET Renew EMAILS

    public static ComposedMail createAssetRenewEmail(AssetRequest assetRequest) {
        String emailContent = """
                Se ha enviado una solicitud de renovacion de prestamo de activo con los siguientes detalles: <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, espere a que se procese su solicitud.
                """.formatted(assetRequest.getAsset().getSubcategory().getName(), assetRequest.getReason(),
                assetRequest.getStatus().getName(), assetRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Renovacion de prestamo de Activo realizada"),
                new MetaData("userName", assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(), "Solicitud de Renovacion de prestamo de Activo realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createAdminReviewAssetRenew(AssetRequest assetRequest) {
        String emailContent = """
                Se ha recibido una nueva solicitud de renovacion de prestamo de activo que requiere su revisión: <br>
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
                new MetaData("emailTitle", "Revision de solicitud de renovacion de prestamo de Activo"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }


    // SPACE Request EMAILS

    public static ComposedMail createSpaceRequestEmail(SpaceRequest spaceRequest, SpaceReservation spaceReservation) {
        String emailContent = """
                Se ha enviado una solicitud de espacio con los siguientes detalles: <br>
                <strong>Motivo del evento:</strong> %s <br>
                <strong>Observaciones del evento:</strong> %s <br>
                <strong>Uso de equipo:</strong> %s <br>
                <strong>Fecha de inicio:</strong> %s <br>
                <strong>Fecha de fin:</strong> %s <br>
                <strong>Espacio:</strong> %s <br>
                <br>
                Por favor, espere a que se procese su solicitud.
                """.formatted(spaceRequest.getEventDesc(), spaceRequest.getEventObs(),
                spaceRequest.getUseEquipment() ? "Sí" : "No", spaceReservation.getStartTime().toString(), spaceReservation.getEndTime().toString(),
                spaceRequest.getSpace().getName());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Espacio realizada"),
                new MetaData("userName", spaceRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(spaceRequest.getUser().getEmail(), "Solicitud de espacio realizada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createAdminReviewSpaceRequest(SpaceRequest spaceRequest, SpaceReservation spaceReservation) {
        String emailContent = """
                Se ha recibido una nueva solicitud de espacio que requiere su revisión: <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Correo del solicitante:</strong> %s <br>
                <strong>Motivo del evento:</strong> %s <br>
                <strong>Observaciones del evento:</strong> %s <br>
                <strong>Uso de equipo:</strong> %s <br>
                <strong>Fecha de inicio:</strong> %s <br>
                <strong>Fecha de fin:</strong> %s <br>
                <strong>Espacio:</strong> %s <br>
                <br>
                Por favor, revise la solicitud y tome la acción correspondiente (aceptar o rechazar).
                """.formatted(spaceRequest.getUser().getName(), spaceRequest.getUser().getEmail(),
                spaceRequest.getEventDesc(), spaceRequest.getEventObs(), spaceRequest.getUseEquipment() ? "Sí" : "No",
                spaceReservation.getStartTime().toString(), spaceReservation.getEndTime().toString(), spaceRequest.getSpace().getName());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Revision de solicitud de Espacio"),
                new MetaData("userName", "Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(spaceRequest.getUser().getEmail(), "Revision de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
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
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    // CANCEL ASSET REQUEST EMAIL

    public static ComposedMail composeUserNotificationCancelAssetRequest(AssetRequest assetRequest, String cancelReason){
        String emailContent = """
                La solicitud de activo ha sido cancelada <br>
                <strong>Motivo de cancelación:</strong> %s <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Placa:</strong> %s <br>
                <strong>Fecha de vencimiento:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información
                """.formatted(cancelReason, assetRequest.getUser().getName(),
                assetRequest.getAsset().getSubcategory().getName(),
                assetRequest.getAsset().getPlateNumber(),
                assetRequest.getExpirationDate());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación de cancelación de solicitud de activo."),
                new MetaData("userName",assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(),"Notificación de cancelación de solicitud",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail composeUserNotificationAcceptAssetRenewalRequest(AssetRequest assetRequest) {
        String emailContent = """
                Su solicitud de renovación de préstamo ha sido aceptada <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Placa:</strong> %s <br>
                <strong>Fecha de vencimiento actualizada:</strong> %s <br>
                """.formatted(assetRequest.getUser().getName(), assetRequest.getAsset().getSubcategory().getName(),
                              assetRequest.getAsset().getPlateNumber(), assetRequest.getExpirationDate());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Notificación de renovación de solicitud de activo."),
                new MetaData("userName", assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent)
        );
        return createEmail(assetRequest.getUser().getEmail(), "Notificación de renovación de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail composeUserNotificationRejectAssetRenewalRequest(AssetRequest assetRequest) {
        String emailContent = """
                Su solicitud de renovación de préstamo ha sido denegada <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Placa:</strong> %s <br>
                <strong>Fecha de vencimiento:</strong> %s <br>
                Por favor, contacte al administrador para más información.
                """.formatted(assetRequest.getUser().getName(),
                assetRequest.getAsset().getSubcategory().getName(),
                assetRequest.getAsset().getPlateNumber(),
                assetRequest.getExpirationDate());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Notificación de cancelación de renovación de solicitud de activo."),
                new MetaData("userName", assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent)
        );
        return createEmail(assetRequest.getUser().getEmail(), "Notificación de renovación de solicitud",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail composeAdminNotificationCancelAssetRequest(AssetRequest assetRequest, String cancelReason){
        String emailContent = """
                La solicitud de activo ha sido cancelada <br>
                <strong>Motivo de cancelación:</strong> %s <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Placa:</strong> %s <br>
                <strong>Fecha de vencimiento:</strong> %s <br>
                <br>
                Por favor, contacte al solicitante para más información
                """.formatted(cancelReason, assetRequest.getUser().getName(),
                assetRequest.getAsset().getSubcategory().getName(),
                assetRequest.getAsset().getPlateNumber(),
                assetRequest.getExpirationDate());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación de cancelación de solicitud de activo."),
                new MetaData("userName","Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(assetRequest.getUser().getEmail(),"Notificación de cancelación de solicitud",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    // CANCEL PRODUCT REQUEST EMAIL

    public static ComposedMail composeUserNotificationCancelProductRequest(ProductRequest productRequest, String cancelReason){
        String emailContent = """
                La solicitud de producto ha sido cancelada <br>
                <strong>Motivo de cancelación:</strong> %s <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Categoría del Producto:</strong> %s <br>
                <strong>Fecha de solicitud:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información
                """.formatted(cancelReason, productRequest.getUser().getName(),
                productRequest.getProduct().getCategory().getName(),
                productRequest.getCreatedAt());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación de cancelación de solicitud de producto."),
                new MetaData("userName",productRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(productRequest.getUser().getEmail(),"Notificación de cancelación de solicitud",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail composeAdminNotificationCancelProductRequest(ProductRequest productRequest, String cancelReason){
        String emailContent = """
                La solicitud de producto ha sido cancelada <br>
                <strong>Motivo de cancelación:</strong> %s <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Categoría del Producto:</strong> %s <br>
                <strong>Fecha de solicitud:</strong> %s <br>
                <br>
                Por favor, contacte al solicitante para más información
                """.formatted(cancelReason, productRequest.getUser().getName(),
                productRequest.getProduct().getCategory().getName(),
                productRequest.getCreatedAt());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación de cancelación de solicitud de producto."),
                new MetaData("userName","Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(productRequest.getUser().getEmail(),"Notificación de cancelación de solicitud",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    // CANCEL SPACE REQUEST EMAIL

    public static ComposedMail composeUserNotificationCancelSpaceRequest(SpaceRequest spaceRequest, String cancelReason) {
        String emailContent = """
                La solicitud de espacio ha sido cancelada <br>
                <strong>Motivo de cancelación:</strong> %s <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Motivo de Solicitud:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información
                """.formatted(cancelReason, spaceRequest.getUser().getName(),
                spaceRequest.getEventDesc());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación de cancelación de solicitud de espacio."),
                new MetaData("userName",spaceRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));
        return createEmail(spaceRequest.getUser().getEmail(),"Notificación de cancelación de solicitud",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail composeAdminNotificationCancelSpaceRequest(SpaceRequest spaceRequest, String cancelReason) {
        String emailContent = """
                La solicitud de espacio ha sido cancelada <br>
                <strong>Motivo de cancelación:</strong> %s <br>
                <strong>Solicitante:</strong> %s <br>
                <strong>Motivo de Solicitud:</strong> %s <br>
                <br>
                Por favor, contacte al solicitante para más información
                """.formatted(cancelReason, spaceRequest.getUser().getName(),
                spaceRequest.getEventDesc());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle","Notificación de cancelación de solicitud de espacio."),
                new MetaData("userName","Usuario Administrador"),
                new MetaData("emailContent", emailContent));
        return createEmail(spaceRequest.getUser().getEmail(),"Notificación de cancelación de solicitud",
                EMailTemplate.GENERIC_EMAIL,metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    // Accept Request Email

    public static ComposedMail createProductRequestAcceptedEmail(ProductRequest productRequest) {
        String emailContent = """
                Su solicitud de producto ha sido aceptada con los siguientes detalles: <br>
                <strong>Producto (Categoría):</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información.
                """.formatted(productRequest.getProduct().getCategory().getName(), productRequest.getReason(),
                productRequest.getStatus().getName(), productRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Producto aceptada"),
                new MetaData("userName", productRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(productRequest.getUser().getEmail(), "Solicitud de producto aceptada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createProductRequestRejectEmail(ProductRequest productRequest) {
        String emailContent = """
                Su solicitud de producto ha sido rechazada con los siguientes detalles: <br>
                <strong>Producto (Categoría):</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información.
                """.formatted(productRequest.getProduct().getCategory().getName(), productRequest.getReason(),
                productRequest.getStatus().getName(), productRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Producto rechazada"),
                new MetaData("userName", productRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(productRequest.getUser().getEmail(), "Solicitud de producto rechazada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createAssetRequestAcceptedEmail(AssetRequest assetRequest){
        String emailContent = """
                Su solicitud de activo ha sido aceptada con los siguientes detalles: <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información.
                """.formatted(assetRequest.getAsset().getSubcategory().getName(), assetRequest.getReason(),
                assetRequest.getStatus().getName(), assetRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Activo aceptada"),
                new MetaData("userName", assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(assetRequest.getUser().getEmail(), "Solicitud de activo aceptada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createAssetRequestRejectEmail(AssetRequest assetRequest){
        String emailContent = """
                Su solicitud de activo ha sido rechazada con los siguientes detalles: <br>
                <strong>Subcategoría del Activo:</strong> %s <br>
                <strong>Razón:</strong> %s <br>
                <strong>Estado de solicitud:</strong> %s <br>
                <strong>Fecha de creación:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información.
                """.formatted(assetRequest.getAsset().getSubcategory().getName(), assetRequest.getReason(),
                assetRequest.getStatus().getName(), assetRequest.getCreatedAt().toString());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Activo rechazada"),
                new MetaData("userName", assetRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(assetRequest.getUser().getEmail(), "Solicitud de activo rechazada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createSpaceRequestAcceptEmail(SpaceRequest spaceRequest){
        String emailContent = """
                Su solicitud de espacio ha sido aceptada con los siguientes detalles: <br>
                <strong>Motivo del evento:</strong> %s <br>
                <strong>Observaciones del evento:</strong> %s <br>
                <strong>Uso de equipo:</strong> %s <br>
                <strong>Fecha de inicio:</strong> %s <br>
                <strong>Fecha de fin:</strong> %s <br>
                <strong>Espacio:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información.
                """.formatted(spaceRequest.getEventDesc(), spaceRequest.getEventObs(),
                spaceRequest.getUseEquipment() ? "Sí" : "No", spaceRequest.getCreatedAt().toString(), spaceRequest.getCreatedAt().toString(),
                spaceRequest.getSpace().getName());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Espacio aceptada"),
                new MetaData("userName", spaceRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(spaceRequest.getUser().getEmail(), "Solicitud de espacio aceptada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

    public static ComposedMail createSpaceRequestRejectEmail(SpaceRequest spaceRequest){
        String emailContent = """
                Su solicitud de espacio ha sido rechazada con los siguientes detalles: <br>
                <strong>Motivo del evento:</strong> %s <br>
                <strong>Observaciones del evento:</strong> %s <br>
                <strong>Uso de equipo:</strong> %s <br>
                <strong>Fecha de inicio:</strong> %s <br>
                <strong>Fecha de fin:</strong> %s <br>
                <strong>Espacio:</strong> %s <br>
                <br>
                Por favor, contacte al administrador para más información.
                """.formatted(spaceRequest.getEventDesc(), spaceRequest.getEventObs(),
                spaceRequest.getUseEquipment() ? "Sí" : "No", spaceRequest.getCreatedAt().toString(), spaceRequest.getCreatedAt().toString(),
                spaceRequest.getSpace().getName());
        List<MetaData> metaData = List.of(
                new MetaData("emailTitle", "Solicitud de Espacio rechazada"),
                new MetaData("userName", spaceRequest.getUser().getName()),
                new MetaData("emailContent", emailContent));

        return createEmail(spaceRequest.getUser().getEmail(), "Solicitud de espacio rechazada",
                EMailTemplate.GENERIC_EMAIL, metaData, List.of(IMPACT_LOGO_IMAGE_WHITE, UCR_CIMPA_BANNER));
    }

}
