package com.impact.brain.request.repository;

import com.impact.brain.request.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Isaac F. B. C.
 * @since 10/3/2024 - 10:42 AM
 */
public interface RequestRepository extends JpaRepository<Request, Integer> {
}
