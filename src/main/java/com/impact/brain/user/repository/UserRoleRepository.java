package com.impact.brain.user.repository;

import com.impact.brain.user.entity.User;
import com.impact.brain.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {


    List<UserRole> findAll();
    UserRole findByName(String role);
}
