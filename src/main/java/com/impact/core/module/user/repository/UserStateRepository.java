package com.impact.core.module.user.repository;

import com.impact.core.module.user.enun.EUserState;
import com.impact.core.module.user.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {
    Optional<UserState> findByName(EUserState name);
}
