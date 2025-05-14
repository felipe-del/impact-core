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

    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * Schedules a notification for an asset request based on its expiration date.
     * The notification is scheduled for one month before the expiration date, or immediately sent if the date has passed.
     * <p>
     * If a notification is already scheduled for the given asset request, it will be cancelled before scheduling a new one.
     *
     * @param assetRequest the {@link AssetRequest} for which the notification should be scheduled.
     */
    public void scheduleNotification(AssetRequest assetRequest) {
        cancelNotification(Long.valueOf(assetRequest.getId()));

        try {
            LocalDate expirationDate = assetRequest.getExpirationDate();
            LocalDate notificationDate = expirationDate.minusMonths(1);
            Instant notificationInstant = notificationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant now = Instant.now();

            if (now.isBefore(notificationInstant)) {
                ScheduledFuture<?> future = taskScheduler.schedule(
                        () -> sendNotification(assetRequest),
                        notificationInstant
                );
                scheduledTasks.put(Long.valueOf(assetRequest.getId()), future);
                log.info("Notificación programada para AssetRequest ID {} el día {}", assetRequest.getId(), notificationDate);
            } else {
                log.info("La fecha de notificación ya pasó para AssetRequest ID {}, enviando notificación inmediatamente.", assetRequest.getId());
                sendNotification(assetRequest);
            }
        } catch (Exception e) {
            log.error("Error al programar notificación para AssetRequest ID {}: {}", assetRequest.getId(), e.getMessage(), e);
        }
    }

    /**
     * Cancels a previously scheduled notification for an asset request.
     * If no notification was scheduled for the given asset request, no action is taken.
     *
     * @param requestId the {@link Long} identifier of the asset request for which the notification should be cancelled.
     */
    public void cancelNotification(Long requestId) {
        ScheduledFuture<?> future = scheduledTasks.remove(requestId);
        if (future != null) {
            future.cancel(true);
            log.info("Notificación cancelada para AssetRequest ID {}", requestId);
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
