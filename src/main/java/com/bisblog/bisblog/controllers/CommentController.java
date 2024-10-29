package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.services.CommentService;
import com.bisblog.bisblog.services.UserService;
import org.apache.coyote.Response;
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

    // @desc Get all comments of a post
    // @route GET /api/posts/{postId}/comments
    // @access Public
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getALlComments(@PathVariable UUID postId) {
        return new ResponseEntity<>(commentService.getAllComments(postId), HttpStatus.OK);
    }

    // @desc Create a comment
    // @route POST /api/posts/{postId}/comments
    // @access Private
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable UUID postId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return new ResponseEntity<>(commentService.createComment(postId, comment, user), HttpStatus.CREATED);
    }

    // @desc Get all replies of a comment
    // @route GET /api/comments/{commentId}/replies
    // @access Public
    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<List<CommentResponse>> getAllRepliesByCommentId(@PathVariable UUID commentId) {
        return new ResponseEntity<>(commentService.getAllRepliesByCommentId(commentId), HttpStatus.OK);
    }

    // @desc Create a reply of a comment
    // @route POST /api/comments/{commentId}/replies
    // @access Private
    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<CommentResponse> createCommentReply(@PathVariable UUID commentId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        return new ResponseEntity<>(commentService.createCommentReply(commentId, comment, user), HttpStatus.CREATED);
    }

    // @desc Update a comment
    // @route PUT /api/comments/{commentId}
    // @access Private
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable UUID commentId, @RequestBody CommentRequest comment, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var response = commentService.updateComment(commentId, comment, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // @desc Delete a comment
    // @route DELETE /api/comments/{commentId}
    // @access Private
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        commentService.deleteComment(commentId, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
