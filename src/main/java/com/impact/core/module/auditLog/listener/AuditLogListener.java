package com.impact.core.module.auditLog.listener;

import com.impact.core.module.auditLog.service.AuditService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditLogListener {

    private static AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        AuditLogListener.auditService = auditService;
    }

    @PostPersist
    public void logInsert(Object entity) {
        logAction("INSERT", entity);
    }

    @PostUpdate
    public void logUpdate(Object entity) {
        logAction("UPDATE", entity);
    }

    @PostRemove
    public void logDelete(Object entity) {
        logAction("DELETE", entity);
    }

    private void logAction(String action, Object entity) {
        String entityName = entity.getClass().getSimpleName();

        /*User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            user = userService.findById(userDetails.getId());
        }*/

        auditService.logAction(
                entityName,
                action,
                entity.toString(),
                null
        );
    }

}