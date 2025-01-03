package com.impact.core.module.auditLog.service;

import com.impact.core.module.auditLog.entity.AuditLog;
import com.impact.core.module.auditLog.repository.AuditLogRepository;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service("auditService")
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void logAction(String entityName, String action, String details, User user) {
        AuditLog auditLog = AuditLog.builder()
                .entityName(entityName)
                .action(action)
                .details(details)
                .timestamp(Instant.now()) // TODO: do this in entity
                .user(user)
                .build();

        auditLogRepository.save(auditLog);
    }
}
