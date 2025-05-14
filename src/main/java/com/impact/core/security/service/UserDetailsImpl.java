package com.impact.core.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.impact.core.module.user.entity.User;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Implementation of Spring Security's {@link UserDetails} interface, used to authenticate
 * and authorize users based on {@link User} entity
 * <p>
 *     This class wraps user details such as id, username, email, password and role-based
 *     authorities. It is used by Spring Security during the authentication process to verify
 *     credentials and grant access
 * </p>
 */
@Getter
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final String username;
    private final String email;

    @JsonIgnore
    private String password;

    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructs a new {@code UserDetailsImpl} instance with the provided user details and authorities
     * @param id
     * The user's unique identifier
     * @param username
     * The user's username or display name
     * @param email
     * The user's email address
     * @param password
     * The user's hashed password (ignored in JSON responses)
     * @param authorities
     * Collection of granted authorities to the user
     */
    public UserDetailsImpl(Integer id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Builds a {@code UserDetailsImpl} instance from a domain {@link User} entity.
     * @param user
     * The user entity from which to extract the authentication details
     * @return
     * A {@code UserDetailsImpl} wrapping the user's roles and credentials
     */
    public static UserDetailsImpl build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name());

        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Comprares this {@code UserDetailsImpl} instance to another based on user ID.
     * @param o
     * The object to compare
     * @return
     * {@code true} if the IDs match, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
