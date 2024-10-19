package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.RegisterRequest;
import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.repositories.UserRepository;
import com.bisblog.bisblog.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public RegisterResponse getUser(@AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        return userService.getUser(user);
    }

    @PostMapping()
    public RegisterResponse createAdmin(@RequestBody  RegisterRequest registerRequest, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return userService.createAdmin(registerRequest, user);
    }

    @PutMapping("/current")
    public RegisterResponse updateUser(@RequestBody RegisterRequest registerRequest, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return userService.updateUser(registerRequest, user);
    }

    @DeleteMapping("/current")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var result =  userService.deleteUser(user);

        if (result)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
