package com.ggkps.kawasip.services;

import com.ggkps.kawasip.models.RegisterRequest;
import com.ggkps.kawasip.config.JwtService;
import com.ggkps.kawasip.models.AuthenticationRequest;
import com.ggkps.kawasip.models.AuthenticationResponse;
import com.ggkps.kawasip.types.Role;
import com.ggkps.kawasip.entities.User;
import com.ggkps.kawasip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {


        if(repository.findByEmail(request.getEmail()).isPresent()){
            return AuthenticationResponse
                    .builder()
                    .message("Email already used")
                    .build();
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .companyName(request.getCompanyName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .message("Account created")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
