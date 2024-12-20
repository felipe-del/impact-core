package com.impact.core.module.user.service;


import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userRoleService")
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRole findByName(EUserRole userRoleName) {
        return userRoleRepository.findByName(userRoleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + userRoleName + " no encontrado."));
    }

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

}
