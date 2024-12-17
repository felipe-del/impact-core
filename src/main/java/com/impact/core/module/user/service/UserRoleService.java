package com.impact.core.module.user.service;


import com.impact.core.module.user.entity.ERole;
import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userRoleService")
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRole findByName(ERole userRoleName) {
        return userRoleRepository.findByName(userRoleName)
                .orElseThrow(() -> new RuntimeException("Error: Role " + userRoleName + " is not found."));
    }

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

}
