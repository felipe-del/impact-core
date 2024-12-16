package com.impact.brain.user.service.user;

import com.impact.brain.user.entity.User;

public interface UserService {
    User findByEmail(String email);
}
