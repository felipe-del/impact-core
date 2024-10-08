package com.impact.brain.request.repository;

import com.impact.brain.request.entity.ResourceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Isaac F. B. C.
 * @since 10/3/2024 - 2:47 PM
 */
public interface ResourceRequestStatusRepository extends JpaRepository<ResourceRequestStatus, Integer> {
    ResourceRequestStatus findByName(String name);
}
