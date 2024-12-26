package com.impact.core.module.user.payload;

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
    private String roleName;
    private String stateName;
}