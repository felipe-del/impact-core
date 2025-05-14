package com.impact.core.module.auditLog.repository;

import com.impact.core.module.auditLog.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link AuditLog} entities.
 * This interface provides CRUD operations and database queries for managing audit logs.
 * It extends {@link JpaRepository}, enabling automatic implementation of common data access methods.
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
