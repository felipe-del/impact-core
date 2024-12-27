package com.impact.core.module.spaceStatus.mapper;

import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpaceStatusMapper {
    public SpaceStatusResponse toDTO(SpaceStatus spaceStatus) {
        return SpaceStatusResponse.builder()
                .id(spaceStatus.getId())
                .name(spaceStatus.getName().toString())
                .description(spaceStatus.getDescription())
                .build();
    }
}
