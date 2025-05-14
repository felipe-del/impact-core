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

/**
 * Service responsible for scheduling notifications related to asset requests.
 * <p>
 * This service allows scheduling notifications for asset request expiration dates. It utilizes
 * a thread pool task scheduler to schedule the notifications and ensures that notifications are sent
 * in a timely manner based on the expiration date.
 */
@Service
@RequiredArgsConstructor
public class DynamicSchedulerService {
    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulerService.class);

    private final ThreadPoolTaskScheduler taskScheduler;
    private final MailService mailService;

    // Soporta múltiples tareas programadas por cada AssetRequest
    private final ConcurrentHashMap<Long, List<ScheduledFuture<?>>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * Schedules notifications for the specified {@link AssetRequest}.
     * <p>
     * Notifications are scheduled for 2 months, 1 month, and 2 days before the asset's expiration date.
     * If any of the calculated dates are in the past, the notification is sent immediately.
     * Any previously scheduled tasks for the same request ID will be canceled before new ones are scheduled.
     * </p>
     *
     * @param assetRequest The {@link AssetRequest} for which notifications should be scheduled.
     */
    public void scheduleNotification(AssetRequest assetRequest) {
        cancelNotification(Long.valueOf(assetRequest.getId())); // Cancel previously scheduled tasks

        try {
            LocalDate expirationDate = assetRequest.getExpirationDate();
            Instant now = Instant.now();
            List<ScheduledFuture<?>> futures = new ArrayList<>();

            futures.add(scheduleSingleNotification(assetRequest, expirationDate.minusMonths(2), now, "2 months before"));
            futures.add(scheduleSingleNotification(assetRequest, expirationDate.minusMonths(1), now, "1 month before"));
            futures.add(scheduleSingleNotification(assetRequest, expirationDate.minusDays(2), now, "2 days before"));

            scheduledTasks.put(Long.valueOf(assetRequest.getId()), futures);
        } catch (Exception e) {
            log.error("Error scheduling notifications for AssetRequest ID {}: {}", assetRequest.getId(), e.getMessage(), e);
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
     * Cancels a previously scheduled notification for an asset request.
     * If no notification was scheduled for the given asset request, no action is taken.
     *
     * @param requestId the {@link Long} identifier of the asset request for which the notification should be cancelled.
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
     * Sends a notification email when the scheduled task is executed.
     * The email is composed using the {@link MailFactory} and sent via the {@link MailService}.
     * <p>
     * The notification informs the user of an approaching expiration date for the asset request.
     *
     * @param req the {@link AssetRequest} for which the notification should be sent.
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
