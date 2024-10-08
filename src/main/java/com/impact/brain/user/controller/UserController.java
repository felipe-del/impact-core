package com.impact.brain.user.controller;

import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.entity.UserRole;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:21 AM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserDTO>> findInactive(){
        return ResponseEntity.ok(userService.findByState(2));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<UserRole>> findRoles(){
        return ResponseEntity.ok(userService.findAllRoles());
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody List<Map<String, String>> body) {
        if (!body.isEmpty()) {
            String role = body.get(0).get("role");
            userService.updateUserRole(id, role);
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectUser(@PathVariable int id) {
        userService.rejectUser(id);
        return ResponseEntity.ok().build();
    }




}
