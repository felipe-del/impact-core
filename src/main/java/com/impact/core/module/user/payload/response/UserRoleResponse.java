package com.impact.core.module.user.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
    private Integer id;
    private String roleName;
    private String description;
}
