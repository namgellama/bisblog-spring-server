package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.ChangePasswordRequest;
import com.bisblog.bisblog.dtos.RegisterRequest;
import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.entities.User;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.UUID;

public interface UserService {
    User findByEmail(String email);
    RegisterResponse createAdmin(RegisterRequest registerRequest, User user);
    RegisterResponse getUser(User user);

    RegisterResponse updateUser(RegisterRequest registerRequest, User user);

    boolean deleteUser(User user);

    void changePassword(ChangePasswordRequest request, User user);

    String forgotPassword(String email) throws MessagingException;

    String resetPassword(String email, String newPassword);
}
