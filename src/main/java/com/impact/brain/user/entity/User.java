package com.impact.brain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private UserState state;

    public User() {

    }

    public User(UserState state, UserRole role, String password, String email, String name, Integer id) {
        this.state = state;
        this.role = role;
        this.password = password;
        this.email = email;
        this.name = name;
        this.id = id;
    }
}