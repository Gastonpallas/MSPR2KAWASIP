package com.ggkps.kawasip.services;

import com.ggkps.kawasip.config.JwtService;
import com.ggkps.kawasip.entities.User;
import com.ggkps.kawasip.models.AuthenticationResponse;
import com.ggkps.kawasip.models.RegisterRequest;
import com.ggkps.kawasip.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
public class AuthenticationServicesTest {

    @Autowired
    AuthenticationService authenticationService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtService jwtService;

    @Test
    void testRegister_Account_Created() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("Cristiano@gmail.com");
        request.setLastname("Ronaldo");
        request.setFirstname("Cristiano");
        request.setPassword("Siuuuuuuuuuuu7");
        request.setCompanyName("CR7");
        request.setCity("Madeire");
        request.setPostalCode("33400");

        // Act
        User userMock = null;
        Mockito.when(userRepository.findByEmail("Cristiano@gmail.com")).thenReturn(Optional.empty());
        AuthenticationResponse authenticationResponse = authenticationService.register(request);

        // Assert
        assertEquals("Account created", authenticationResponse.getMessage());
    }

    @Test
    void testRegister_Email_Already_Used() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("Cristiano@gmail.com");
        request.setLastname("Ronaldo");
        request.setFirstname("Cristiano");
        request.setPassword("Siuuuuuuuuuuu7");
        request.setCompanyName("CR7");
        request.setCity("Madeire");
        request.setPostalCode("33400");

        // Act
        User userMock = new User();
        Mockito.when(userRepository.findByEmail("Cristiano@gmail.com")).thenReturn(Optional.of(userMock));
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn("test");

        AuthenticationResponse authenticationResponse = authenticationService.register(request);

        // Assert
        assertEquals("Email already used", authenticationResponse.getMessage());
    }


}