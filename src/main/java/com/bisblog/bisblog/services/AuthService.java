package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.dtos.LoginRequest;
import com.bisblog.bisblog.dtos.RegisterRequest;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    String login(LoginRequest request);
}
