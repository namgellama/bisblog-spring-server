package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.DownvoteResponse;
import com.bisblog.bisblog.services.DownvoteService;
import com.bisblog.bisblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DownvoteController {
    private final DownvoteService downvoteService;
    private final UserService userService;

    public DownvoteController(DownvoteService downvoteService, UserService userService, ModelMapper modelMapper) {
        this.downvoteService = downvoteService;
        this.userService = userService;
    }

    // @desc Create a post downvote
    // @route GET /api/posts/{postId}/downvotes
    // @access Private
    @PostMapping("/posts/{postId}/downvotes")
    public ResponseEntity<DownvoteResponse> createPostDownvote(@PathVariable UUID postId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var result = downvoteService.downvotePost(postId, user);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // @desc Create a comment downvote
    // @route GET /api/comments/{commentId}/downvotes
    // @access Private
    @PostMapping("/comments/{commentId}/downvotes")
    public ResponseEntity<DownvoteResponse> createCommentDownvote(@PathVariable UUID commentId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var result = downvoteService.downvoteComment(commentId, user);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
