package com.impact.brain.user.repository;

import com.impact.brain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:22 AM
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("SELECT new User(u.id, u.name, u.email, u.role, u.state) FROM User u")
    List<User> findAll();

}
