package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.services.CommentService;
import com.bisblog.bisblog.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public List<CommentResponse> getALlComments(@PathVariable UUID postId) {
        return commentService.getAllComments(postId);
    }

    @PostMapping
    public CommentResponse createComment(@PathVariable UUID postId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return commentService.createComment(postId, comment, user);
    }

    @GetMapping("/{commentId}/replies")
    public List<CommentResponse> getAllRepliesByCommentId(@PathVariable UUID postId, @PathVariable UUID commentId) {
        return commentService.getAllRepliesByCommentId(postId, commentId);
    }


    @PostMapping("/{commentId}/replies")
    public CommentResponse createCommentReply(@PathVariable UUID postId, @PathVariable UUID commentId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return commentService.createCommentReply(postId, commentId, comment, user);
    }
}
