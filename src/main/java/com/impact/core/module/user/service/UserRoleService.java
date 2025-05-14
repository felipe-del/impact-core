package com.impact.core.module.user.service;


import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.mapper.UserRoleMapper;
import com.impact.core.module.user.payload.response.UserRoleResponse;
import com.impact.core.module.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing user roles.
 */
@Service("userRoleService")
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    /**
     * Retrieves a user role by its enumerated name.
     *
     * @param userRoleName the role enum (e.g., ROLE_ADMINISTRATOR)
     * @return the UserRole entity
     * @throws ResourceNotFoundException if the role is not found
     */
    public UserRole findByName(EUserRole userRoleName) {
        return userRoleRepository.findByName(userRoleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + userRoleName + " no encontrado."));
    }

    /**
     * Retrieves a user role by its ID.
     *
     * @param id the ID of the user role
     * @return the UserRole entity
     * @throws ResourceNotFoundException if the role is not found
     */
    public UserRole findById(Integer id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role con el id: " + id + " no encontrado."));
    }

    /**
     * Returns a list of all user roles as DTOs.
     *
     * @return list of UserRoleResponse objects
     */
    public List<UserRoleResponse> findAll() {
        return userRoleRepository.findAll().stream()
                .map(userRoleMapper::toDTO)
                .toList();
    }

}
