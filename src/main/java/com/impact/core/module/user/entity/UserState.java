package com.impact.core.module.user.entity;

import com.impact.core.module.user.enun.EUserState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_state")
public class UserState {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    private EUserState name;

    @Lob
    @Column(name = "description")
    private String description;

}