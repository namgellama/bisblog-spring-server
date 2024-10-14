package com.bisblog.bisblog.services;

import com.bisblog.bisblog.entities.User;

public interface UserService {
    User findByEmail(String email);
}
