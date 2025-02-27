package com.impact.core.module.spaceStatus.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceStatusResponse {
    private Integer id;
    private String name;
    private String description;
}
