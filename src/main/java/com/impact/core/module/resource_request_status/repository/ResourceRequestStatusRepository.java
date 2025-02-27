package com.impact.core.module.resource_request_status.repository;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRequestStatusRepository extends JpaRepository<ResourceRequestStatus, Integer> {
    Optional<ResourceRequestStatus> findByName(EResourceRequestStatus name);
}
