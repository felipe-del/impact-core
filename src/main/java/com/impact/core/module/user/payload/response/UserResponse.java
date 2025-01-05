package com.impact.core.module.user.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private UserRoleResponse roleName;
    private UserStateResponse stateName;
}