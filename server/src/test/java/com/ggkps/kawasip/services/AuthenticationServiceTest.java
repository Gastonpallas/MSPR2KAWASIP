package com.ggkps.kawasip.services;

import com.ggkps.kawasip.config.JwtService;
import com.ggkps.kawasip.entities.User;
import com.ggkps.kawasip.models.RegisterRequest;
import com.ggkps.kawasip.models.AuthenticationResponse;
import com.ggkps.kawasip.repositories.UserRepository;
import com.ggkps.kawasip.types.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setCity("City");
        request.setPostalCode("12345");
        request.setCompanyName("Company");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("Account created", response.getMessage());
        assertEquals("jwtToken", response.getToken());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterEmailAlreadyUsed() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setCity("City");
        request.setPostalCode("12345");
        request.setCompanyName("Company");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("Email already used", response.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }
}
