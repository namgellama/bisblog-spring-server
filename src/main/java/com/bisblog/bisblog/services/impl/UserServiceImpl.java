package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.ChangePasswordRequest;
import com.bisblog.bisblog.dtos.RegisterRequest;
import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.entities.enums.Role;
import com.bisblog.bisblog.exceptions.UnauthorizedException;
import com.bisblog.bisblog.repositories.UserRepository;
import com.bisblog.bisblog.services.UserService;
import com.bisblog.bisblog.utils.EmailUtil;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailUtil emailUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, EmailUtil emailUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailUtil = emailUtil;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Override
    public RegisterResponse createAdmin(RegisterRequest registerRequest, User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Not authorized");
        }

        var newAdmin = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ADMIN)
                .build();

        return modelMapper.map(userRepository.save(newAdmin), RegisterResponse.class);
    }

    @Override
    public RegisterResponse getUser(User user) {
        var userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return modelMapper.map(userEntity, RegisterResponse.class);
    }

    @Override
    public RegisterResponse updateUser(RegisterRequest registerRequest, User user) {
        var userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        userEntity.setFirstName(registerRequest.getFirstName());
        userEntity.setLastName(registerRequest.getLastName());
        userEntity.setEmail(registerRequest.getEmail());

        return modelMapper.map(userRepository.save(userEntity), RegisterResponse.class);
    }

    @Override
    public boolean deleteUser(User user) {
        var userEntity = userRepository.findById(user.getId());

        if (userEntity == null)
            return false;

        userRepository.deleteById(userEntity.get().getId());
        return true;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, User user) {
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong Password.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public String forgotPassword(String email)  {
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found."));

        try {
            emailUtil.sendResetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send reset password email. Please try again.");
        }

        return "Please check your email to reset your password";
    }

    @Override
    public String resetPassword(String email, String newPassword) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "New password set successfully.";
    }
}
