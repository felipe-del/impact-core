package com.impact.core.module.user.repository;

import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(EUserRole name);
    Optional<UserRole> findById(int id);
    List<UserRole> findAll();
}
