package com.impact.core.module.user.repository;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.enun.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<User> findById(Integer id);
    List<User> findByRole_Name(EUserRole role);
}
