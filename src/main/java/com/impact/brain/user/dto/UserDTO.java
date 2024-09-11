package com.impact.brain.user.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/7/2024 - 5:46 PM
 */
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String role;
    private String state;
    private boolean isAuthenticated;

    public UserDTO(int id, String name, String email, String role, String state) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
