package com.impact.core.module.user.repository;

import com.impact.core.module.user.entity.ERole;
import com.impact.core.module.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(ERole name);

    List<UserRole> findAll();
}
