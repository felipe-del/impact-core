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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class DynamicSchedulerService {
    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulerService.class);

    private final ThreadPoolTaskScheduler taskScheduler;
    private final MailService mailService;

    // Soporta múltiples tareas programadas por cada AssetRequest
    private final ConcurrentHashMap<Long, List<ScheduledFuture<?>>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * Programa notificaciones para 2 meses, 1 mes y 2 días antes de la fecha de vencimiento.
     */
    public void scheduleNotification(AssetRequest assetRequest) {
        cancelNotification(Long.valueOf(assetRequest.getId())); // Cancela todas las previas

        try {
            LocalDate expirationDate = assetRequest.getExpirationDate();
            Instant now = Instant.now();

            List<ScheduledFuture<?>> futures = new ArrayList<>();

            futures.add(scheduleSingleNotification(assetRequest, expirationDate.minusMonths(2), now, "2 meses antes"));
            futures.add(scheduleSingleNotification(assetRequest, expirationDate.minusMonths(1), now, "1 mes antes"));
            futures.add(scheduleSingleNotification(assetRequest, expirationDate.minusDays(2), now, "2 días antes"));

            scheduledTasks.put(Long.valueOf(assetRequest.getId()), futures);
        } catch (Exception e) {
            log.error("Error al programar notificaciones para AssetRequest ID {}: {}", assetRequest.getId(), e.getMessage(), e);
        }
    }

    private ScheduledFuture<?> scheduleSingleNotification(AssetRequest assetRequest, LocalDate notificationDate, Instant now, String label) {
        Instant notificationInstant = notificationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        if (now.isBefore(notificationInstant)) {
            ScheduledFuture<?> future = taskScheduler.schedule(
                    () -> sendNotification(assetRequest),
                    notificationInstant
            );
            log.info("Notificación ({}) programada para AssetRequest ID {} el día {}", label, assetRequest.getId(), notificationDate);
            return future;
        } else {
            log.info("La notificación ({}) ya pasó para AssetRequest ID {}, enviando inmediatamente.", label, assetRequest.getId());
            sendNotification(assetRequest);
            return null;
        }
    }

    /**
     * Cancela todas las notificaciones programadas para un AssetRequest.
     */
    public void cancelNotification(Long requestId) {
        List<ScheduledFuture<?>> futures = scheduledTasks.remove(requestId);
        if (futures != null) {
            for (ScheduledFuture<?> future : futures) {
                if (future != null) future.cancel(true);
            }
            log.info("Todas las notificaciones canceladas para AssetRequest ID {}", requestId);
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
