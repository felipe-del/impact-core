package com.impact.core.module.user.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStateResponse {
    private Integer id;
    private String stateName;
    private String description;
}
