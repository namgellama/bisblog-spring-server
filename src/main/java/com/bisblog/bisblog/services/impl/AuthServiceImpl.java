package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.config.JwtService;
import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.dtos.LoginRequest;
import com.bisblog.bisblog.dtos.RegisterRequest;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.entities.enums.Role;
import com.bisblog.bisblog.repositories.UserRepository;
import com.bisblog.bisblog.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        var userData = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.BLOGGER)
                .build();
        var user = userRepository.save(userData);

        return  RegisterResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public String login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        return jwtService.generateToken(user);
    }
}
