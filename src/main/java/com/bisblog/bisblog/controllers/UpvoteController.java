package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.UpvoteResponse;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.services.UpvoteService;
import com.bisblog.bisblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // @desc Create a post upvote
    // @route GET /api/posts/{postId}/upvotes
    // @access Private
    @PostMapping("/posts/{postId}/upvotes")
    public ResponseEntity<UpvoteResponse> createPostUpvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var result = upvoteService.upvotePost(postId, user);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/comments/{commentId}/upvotes")
    public ResponseEntity<UpvoteResponse> createCommentUpvote(@PathVariable UUID commentId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var result = upvoteService.upvoteComment(commentId, user);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
