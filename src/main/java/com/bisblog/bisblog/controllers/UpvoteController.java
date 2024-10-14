package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.UpvoteResponse;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.services.UpvoteService;
import com.bisblog.bisblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{postId}/upvotes")
public class UpvoteController {
    private final UpvoteService upvoteService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UpvoteController(UpvoteService upvoteService, UserService userService, ModelMapper modelMapper) {
        this.upvoteService = upvoteService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public UpvoteResponse createUpvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return modelMapper.map(upvoteService.upvote(postId, user), UpvoteResponse.class);
    }
}
