package com.impact.core.module.auditLog.service;

import com.impact.core.module.auditLog.entity.AuditLog;
import com.impact.core.module.auditLog.mapper.AuditLogMapper;
import com.impact.core.module.auditLog.payload.response.AuditLogResponse;
import com.impact.core.module.auditLog.repository.AuditLogRepository;
import com.impact.core.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service class for managing and processing audit logs.
 * This service provides functionality to log actions related to entities and retrieve audit logs.
 */
@Service("auditService")
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    /**
     * Logs an action performed on an entity by a user.
     *
     * @param entityName The name of the entity on which the action was performed.
     * @param action The type of action performed (e.g., "INSERT", "UPDATE", "DELETE").
     * @param details Additional details about the action performed.
     * @param user The user who performed the action.
     */
    public void logAction(String entityName, String action, String details, User user) {
        AuditLog auditLog = AuditLog.builder()
                .entityName(entityName)
                .action(action)
                .details(details)
                .user(user)
                .build();

        auditLogRepository.save(auditLog);
    }

    /**
     * Retrieves all audit logs, mapped to a response format.
     *
     * @return A list of {@link AuditLogResponse} objects representing all audit logs.
     */
    public List<AuditLogResponse> findAll() {
        return auditLogRepository.findAll().stream()
                .map(auditLogMapper::toDTO)
                .toList();
    }
}
