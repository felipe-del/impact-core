package com.impact.core.module.user.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.enun.EUserState;
import com.impact.core.module.user.entity.UserState;
import com.impact.core.module.user.mapper.UserStateMapper;
import com.impact.core.module.user.payload.response.UserStateResponse;
import com.impact.core.module.user.repository.UserStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userStateService")
@RequiredArgsConstructor
public class UserStateService {
    private final UserStateRepository userStateRepository;
    private final UserStateMapper userStateMapper;

    public UserState findByName(EUserState userState) {
        return userStateRepository.findByName(userState)
                .orElseThrow(() -> new ResourceNotFoundException("Estado " + userState + " no encontrado."));
    }

    public UserState findById(int id) {
        return userStateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado con id: " + id + " no encontrado."));
    }

    public List<UserStateResponse> findAll() {
        return userStateRepository.findAll().stream()
                .map(userStateMapper::toDTO)
                .toList();
    }

}
