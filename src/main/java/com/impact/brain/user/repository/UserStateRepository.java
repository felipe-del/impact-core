package com.impact.brain.user.repository;

import com.impact.brain.user.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStateRepository extends JpaRepository<UserState, Integer> {
    UserState findByName(String name);
}
