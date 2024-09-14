package com.impact.brain.email.util;

import com.impact.brain.email.dto.MetaData;
import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:57 AM
 */
public class EmailServiceUtil {

    public static SendRequest prepareWelcomeEmail(User user) {
        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("name", user.getName()));
        return new SendRequest(user.getEmail(), "Bienvenido a nuestra plataforma", "welcome-email", metaData);
    }

    public static SendRequest preparePasswordResetEmail(User user, String resetToken) {
        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("name", user.getName()));
        metaData.add(new MetaData("resetCode", resetToken));
        return new SendRequest(user.getEmail(), "Restablecimiento de contraseña", "reset-password-email", metaData);
    }

    public static List<SendRequest> prepareNotificationEmailToAdmin(List<User> users) {
        List<SendRequest> sendRequests = new ArrayList<>();
        for (User user : users) {
            List<MetaData> metaData = new ArrayList<>();
            metaData.add(new MetaData("name", user.getName()));
            metaData.add(new MetaData("email", user.getEmail()));

            SendRequest request = new SendRequest(user.getEmail(),"Registro de un nuevo usuario","notification-new-user",metaData);
            sendRequests.add(request);
        }
        return sendRequests;
    }

}
