package com.impact.core.module.user.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.enun.EUserState;
import com.impact.core.module.user.entity.UserState;
import com.impact.core.module.user.repository.UserStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("userStateService")
@RequiredArgsConstructor
public class UserStateService {
    private final UserStateRepository userStateRepository;

    public UserState findByName(EUserState userState) {
        return userStateRepository.findByName(userState)
                .orElseThrow(() -> new ResourceNotFoundException("Estado " + userState + " no encontrado."));
    }

}
