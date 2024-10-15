package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.services.CommentService;
import com.bisblog.bisblog.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponse> getALlComments(@PathVariable UUID postId) {
        return commentService.getAllComments(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentResponse createComment(@PathVariable UUID postId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return commentService.createComment(postId, comment, user);
    }

    @GetMapping("/comments/{commentId}/replies")
    public List<CommentResponse> getAllRepliesByCommentId(@PathVariable UUID commentId) {
        return commentService.getAllRepliesByCommentId(commentId);
    }

    @PostMapping("/comments/{commentId}/replies")
    public CommentResponse createCommentReply(@PathVariable UUID commentId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return commentService.createCommentReply(commentId, comment, user);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var result = commentService.deleteComment(commentId, user);

        if (!result) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
