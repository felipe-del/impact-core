package com.impact.brain.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private UserState state;

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    // ---

    public User(Integer id, String name, String email, String password, UserRole role, UserState state) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
    }

    // Nuevo constructor que omite el campo 'password'
    public User(Integer id, String name, String email, UserRole role, UserState state) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.state = state;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=' NULL" + '\'' +
                ", role=" + role.getName() +
                ", state=" + state.getName() +
                '}';
    }
}