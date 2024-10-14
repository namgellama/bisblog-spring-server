package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.services.UpvoteService;
import com.bisblog.bisblog.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{postId}/upvotes")
public class UpvoteController {
    private final UpvoteService upvoteService;
    private final UserService userService;

    public UpvoteController(UpvoteService upvoteService, UserService userService) {
        this.upvoteService = upvoteService;
        this.userService = userService;
    }

    @PostMapping
    public Upvote createUpvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return upvoteService.upvote(postId, user);
    }
}
