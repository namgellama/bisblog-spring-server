package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.repositories.UserRepository;
import com.bisblog.bisblog.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
