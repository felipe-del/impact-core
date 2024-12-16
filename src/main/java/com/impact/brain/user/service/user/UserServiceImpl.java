package com.impact.brain.user.service.user;

import com.impact.brain.configuration.exception.customError.NotFoundException;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No existe usuario con correo: " + email));
    }

}
