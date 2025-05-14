package com.impact.core.module.auditLog.mapper;

import com.impact.core.module.auditLog.entity.AuditLog;
import com.impact.core.module.auditLog.payload.response.AuditLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting {@link AuditLog} entities to {@link AuditLogResponse} DTOs.
 * This class is used to map data from the entity to the response payload.
 */
@Component
@RequiredArgsConstructor
public class AuditLogMapper {

    /**
     * Converts an {@link AuditLog} entity to an {@link AuditLogResponse} DTO.
     *
     * @param auditLog The {@link AuditLog} entity to be converted.
     * @return An {@link AuditLogResponse} DTO containing the audit log data.
     */
    public AuditLogResponse toDTO(AuditLog auditLog) {
        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .entityName(auditLog.getEntityName())
                .action(auditLog.getAction())
                .details(auditLog.getDetails())
                .timestamp(auditLog.getTimestamp())
                .user(null)
                .build();
    }
}
