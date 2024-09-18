package com.impact.brain.user.service;

import com.impact.brain.user.entity.User;
import com.impact.brain.user.repository.UserRepository;
import com.impact.brain.user.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById_UserExists() {
        // simulate user from db
        User user = new User();
        user.setId(1);
        user.setName("Felipe Escalante");
        user.setEmail("felipe.escalante@ucr.ac.cr");

        // simulate repository -> findById
        when(userRepository.findById(1)).thenReturn(Optional.of(user));


        User foundUser = userService.findById(1);

        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());
        assertEquals("Felipe Escalante", foundUser.getName());
        assertEquals("felipe.escalante@ucr.ac.cr", foundUser.getEmail());

        // Verify repository call
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_UserNotFound() {
        // Simulate to not find the user in the db
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userService.findById(1);
        });

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testFindByEmail_UserExists() {
        // simulate user from db
        User user = new User();
        user.setId(1);
        user.setName("Felipe Escalante");
        user.setEmail("felipe.escalante@ucr.ac.cr");

        when(userRepository.findByEmail("felipe.escalante@ucr.ac.cr")).thenReturn(user);

        User foundUser = userService.findByEmail("felipe.escalante@ucr.ac.cr");

        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());
        assertEquals("Felipe Escalante", foundUser.getName());
        assertEquals("felipe.escalante@ucr.ac.cr", foundUser.getEmail());

        verify(userRepository, times(1)).findByEmail("felipe.escalante@ucr.ac.cr");
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        // Simular que no se encuentra el usuario en el repositorio
        when(userRepository.findByEmail("nonexistentuser@example.com")).thenReturn(null);

        // Llamada al método a probar y verificar que devuelve null
        User foundUser = userService.findByEmail("nonexistentuser@example.com");

        // Verificar que el usuario no se encontró
        assertNull(foundUser);

        // Verificar que el repositorio se llamó correctamente
        verify(userRepository, times(1)).findByEmail("nonexistentuser@example.com");
    }
}
