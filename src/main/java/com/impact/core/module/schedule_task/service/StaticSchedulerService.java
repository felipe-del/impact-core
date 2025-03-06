package com.impact.core.module.schedule_task.service;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.assetRequest.service.AssetRequestService;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class StaticSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(StaticSchedulerService.class);

    private final MailService mailService;
    private final AssetRequestService assetRequestService;

    // Ejecutar todos los días a las 7:00am
    /*@Scheduled(cron = "0 0 7 * * *")
    public void checkAssetExpirationDate() {
        LocalDate notificationDate = LocalDate.now().plusDays(2);

        for (AssetRequestDTOResponse req : assetRequestService.findAll()) {
            if (req.getExpirationDate() == null) continue;

            try {
                if (LocalDate.parse(req.getExpirationDate()).isEqual(notificationDate)) {
                    AssetRequest asset = assetRequestService.findById(req.getId());
                    if (asset != null) {
                        mailService.sendComposedEmail(
                                MailFactory.composeUserNotificationExpirationDateAssetRequest(asset)
                        );
                    } else {
                        log.error("No se encontró la solicitud de activo con ID {}", req.getId());
                    }
                }
            } catch (DateTimeParseException e) {
                log.error("Error al parsear la fecha de expiración para la solicitud con ID {}: {}",
                        req.getId(), req.getExpirationDate(), e);
            }
        }
    }*/
}
