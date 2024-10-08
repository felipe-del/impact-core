package com.impact.brain.request.repository;

import com.impact.brain.request.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Isaac F. B. C.
 * @since 10/3/2024 - 11:01 AM
 */
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer> {

}
