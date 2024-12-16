package com.impact.brain.user.service;

import com.impact.brain.user.entity.User;

import java.util.Optional;

public interface UserService {
    User findByEmail(String email);
}
