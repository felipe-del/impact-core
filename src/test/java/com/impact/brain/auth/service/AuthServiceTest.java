package com.impact.brain.auth.service;
import com.impact.brain.auth.service.implement.AuthService;
import com.impact.brain.email.service.impl.EmailSendService;
import com.impact.brain.security.UserDetailsImp;
import com.impact.brain.user.dto.UserDTO;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.impl.UserService;
import com.impact.brain.userToken.service.impl.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailSendService emailService;

    @Mock
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateTest() throws Exception {

        String email = "raquel.alfaro@ucr.ac.cr";
        String password = "@IMPACT24";

        User user = new User();
        user.setEmail(email);

        UserDetailsImp userDetails = mock(UserDetailsImp.class);
        when(userDetails.getUser()).thenReturn(user);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // simulate auth
        when(request.getRemoteUser()).thenReturn(email);
        when(userService.mapToUserDTO(any(User.class))).thenReturn(new UserDTO());


        UserDTO userDTO = authService.authenticate(email, password, request);

        assertNotNull(userDTO);
        verify(userService, times(1)).mapToUserDTO(user);
    }


}

