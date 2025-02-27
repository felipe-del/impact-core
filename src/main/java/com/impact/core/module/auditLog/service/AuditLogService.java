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

@Service("auditService")
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public void logAction(String entityName, String action, String details, User user) {
        AuditLog auditLog = AuditLog.builder()
                .entityName(entityName)
                .action(action)
                .details(details)
                .user(user)
                .build();

        auditLogRepository.save(auditLog);
    }

    public List<AuditLogResponse> findAll() {
        return auditLogRepository.findAll().stream()
                .map(auditLogMapper::toDTO)
                .toList();
    }
}
