package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.RegisterRequest;
import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {
    User findByEmail(String email);
    RegisterResponse createAdmin(RegisterRequest registerRequest, User user);
    RegisterResponse getUser(User user);

    RegisterResponse updateUser(RegisterRequest registerRequest, User user);

    boolean deleteUser(User user);
}
