package com.impact.core.module.schedule_task.service;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class DynamicSchedulerService {
    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulerService.class);

    private final ThreadPoolTaskScheduler taskScheduler;
    private final MailService mailService;

    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * Programa una notificación para 2 días antes de la fecha de vencimiento de un AssetRequest.
     */
    public void scheduleNotification(AssetRequest assetRequest) {
        cancelNotification(Long.valueOf(assetRequest.getId())); // Cancela la anterior si existe

        try {
            LocalDate expirationDate = assetRequest.getExpirationDate();
            LocalDate notificationDate = expirationDate.minusMonths(1);
            Instant notificationInstant = notificationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant now = Instant.now();

            if (now.isBefore(notificationInstant)) {
                // Programar la notificación normalmente
                ScheduledFuture<?> future = taskScheduler.schedule(
                        () -> sendNotification(assetRequest),
                        notificationInstant
                );
                scheduledTasks.put(Long.valueOf(assetRequest.getId()), future);
                log.info("Notificación programada para AssetRequest ID {} el día {}", assetRequest.getId(), notificationDate);
            } else {
                // La fecha de notificación ya pasó, enviar inmediatamente
                log.info("La fecha de notificación ya pasó para AssetRequest ID {}, enviando notificación inmediatamente.", assetRequest.getId());
                sendNotification(assetRequest);
            }
        } catch (Exception e) {
            log.error("Error al programar notificación para AssetRequest ID {}: {}", assetRequest.getId(), e.getMessage(), e);
        }
    }
    /**
     * Cancela la notificación programada para un AssetRequest.
     */
    public void cancelNotification(Long requestId) {
        ScheduledFuture<?> future = scheduledTasks.remove(requestId);
        if (future != null) {
            future.cancel(true);
            log.info("Notificación cancelada para AssetRequest ID {}", requestId);
        }
    }
    /**
     * Envía la notificación cuando se ejecuta la tarea programada.
     */
    private void sendNotification(AssetRequest req) {
        if (req != null) {
            mailService.sendComposedEmail(
                    MailFactory.composeUserNotificationExpirationDateAssetRequest(req)
            );
            log.info("Notificación enviada para AssetRequest ID {}", req.getId());
        } else {
            log.error("No se encontró la solicitud de activo para enviar notificación.");
        }
    }
}
