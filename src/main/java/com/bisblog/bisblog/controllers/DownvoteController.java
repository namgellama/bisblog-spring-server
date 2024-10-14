package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.DownvoteResponse;
import com.bisblog.bisblog.services.DownvoteService;
import com.bisblog.bisblog.services.UserService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public DownvoteController(DownvoteService downvoteService, UserService userService, ModelMapper modelMapper) {
        this.downvoteService = downvoteService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public DownvoteResponse createDownvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return modelMapper.map(downvoteService.downvote(postId, user), DownvoteResponse.class);
    }
}
