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
@RequestMapping("/api")
public class UpvoteController {
    private final UpvoteService upvoteService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UpvoteController(UpvoteService upvoteService, UserService userService, ModelMapper modelMapper) {
        this.upvoteService = upvoteService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/posts/{postId}/upvotes")
    public UpvoteResponse createPostUpvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return modelMapper.map(upvoteService.upvotePost(postId, user), UpvoteResponse.class);
    }

    @PostMapping("/comments/{commentId}/upvotes")
    public UpvoteResponse createCommentUpvote(@PathVariable UUID commentId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        System.out.println(userDetails.getUsername());

        return modelMapper.map(upvoteService.upvoteComment(commentId, user), UpvoteResponse.class);
    }
}
