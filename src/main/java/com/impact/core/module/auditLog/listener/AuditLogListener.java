package com.impact.core.module.auditLog.listener;

import com.impact.core.module.auditLog.service.AuditLogService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener class responsible for intercepting entity lifecycle events (INSERT, UPDATE, DELETE)
 * and logging them as audit log entries using the AuditLogService.
 */
@Component
@RequiredArgsConstructor
public class AuditLogListener {

    private static AuditLogService auditLogService;

    /**
     * Sets the AuditLogService instance via Spring's dependency injection.
     * This method is called during the initialization of the listener.
     *
     * @param auditLogService The AuditLogService to be set.
     */
    @Autowired
    public void setAuditLogService(AuditLogService auditLogService) {
        AuditLogListener.auditLogService = auditLogService;
    }

    /**
     * Logs an insert action when an entity is persisted to the database.
     *
     * @param entity The entity being inserted into the database.
     */
    @PostPersist
    public void logInsert(Object entity) {
        logAction("INSERT", entity);
    }

    /**
     * Logs an update action when an entity is updated in the database.
     *
     * @param entity The entity being updated in the database.
     */
    @PostUpdate
    public void logUpdate(Object entity) {
        logAction("UPDATE", entity);
    }

    /**
     * Logs a delete action when an entity is removed from the database.
     *
     * @param entity The entity being deleted from the database.
     */
    @PostRemove
    public void logDelete(Object entity) {
        logAction("DELETE", entity);
    }

    /**
     * Helper method to log the action performed on an entity.
     *
     * @param action The action performed on the entity (INSERT, UPDATE, DELETE).
     * @param entity The entity on which the action was performed.
     */
    private void logAction(String action, Object entity) {
        String entityName = entity.getClass().getSimpleName();

        auditLogService.logAction(
                entityName,
                action,
                entity.toString(),
                null
        );
    }

}