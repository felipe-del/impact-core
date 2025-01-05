package com.impact.core.module.user.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.user.enun.EUserRole;
import com.impact.core.module.user.mapper.MyUserMapper;
import com.impact.core.module.user.payload.request.UserRequest;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MyUserMapper myUserMapper;

    public User findImpactUser(String email) { // Use only in login and authentication
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario con email: " + email));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con email: " + email));
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserResponse update(int id, UserRequest user) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        return myUserMapper.toDTO(userRepository.save(userToUpdate));
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con id: " + id));
    }

    public List<User> getAllAdmins() {
        return userRepository.findByRole_Name(EUserRole.ROLE_ADMINISTRATOR);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(myUserMapper::toDTO)
                .toList();
    }
}
