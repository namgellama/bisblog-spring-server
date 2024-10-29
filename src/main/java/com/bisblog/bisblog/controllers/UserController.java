package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.ChangePasswordRequest;
import com.bisblog.bisblog.dtos.RegisterRequest;
import com.bisblog.bisblog.dtos.RegisterResponse;
import com.bisblog.bisblog.repositories.UserRepository;
import com.bisblog.bisblog.services.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    // @desc Get current logged-in user
    // @route GET /api/users/current
    // @access Private
    @GetMapping("/current")
    public ResponseEntity<RegisterResponse> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return new ResponseEntity<>(userService.getUser(user), HttpStatus.OK);
    }

    // @desc Create another admin
    // @route POST /api/users
    // @access Private/Admin
    @PostMapping
    public ResponseEntity<RegisterResponse> createAdmin(@RequestBody  RegisterRequest registerRequest, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return new ResponseEntity<>(userService.createAdmin(registerRequest, user), HttpStatus.CREATED);
    }

    // @desc Update current logged-in user
    // @route PUT /api/users/current
    // @access Private
    @PutMapping("/current")
    public ResponseEntity<RegisterResponse> updateUser(@RequestBody RegisterRequest registerRequest, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return new ResponseEntity<>(userService.updateUser(registerRequest, user), HttpStatus.OK);
    }

    // @desc Forgot password
    // @route PUT /api/users/forgot-password?email={email}
    // @access Private
    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    // @desc Change password
    // @route PATCH /api/users/change-password
    // @access Private
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        userService.changePassword(request, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // @desc Reset password
    // @route PUT /api/users/reset-password?email={email}
    // @access Private
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestHeader String newPassword) {
        return new ResponseEntity<>(userService.resetPassword(email, newPassword), HttpStatus.OK);
    }

    // @desc Delete current logged-in user
    // @route DELETE /api/users/current
    // @access Private
    @DeleteMapping("/current")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        userService.deleteUser(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
