package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.services.DownvoteService;
import com.bisblog.bisblog.services.UpvoteService;
import com.bisblog.bisblog.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{postId}/downvotes")
public class DownvoteController {
    private final DownvoteService downvoteService;
    private final UserService userService;

    public DownvoteController(DownvoteService downvoteService, UserService userService) {
        this.downvoteService = downvoteService;
        this.userService = userService;
    }

    @PostMapping
    public Downvote createDownvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return downvoteService.downvote(postId, user);
    }
}
