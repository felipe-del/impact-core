package com.impact.core.module.auditLog.mapper;

import com.impact.core.module.auditLog.entity.AuditLog;
import com.impact.core.module.auditLog.payload.response.AuditLogResponse;
import com.impact.core.module.user.mapper.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditLogMapper {

    //public final MyUserMapper userMapper;

    public AuditLogResponse toDTO(AuditLog auditLog) {
        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .entityName(auditLog.getEntityName())
                .action(auditLog.getAction())
                .details(auditLog.getDetails())
                .timestamp(auditLog.getTimestamp())
                //.user(userMapper.toDTO(auditLog.getUser()))
                .user(null)
                .build();
    }
}
