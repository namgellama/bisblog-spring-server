package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.PostRequest;
import com.bisblog.bisblog.dtos.PostResponse;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.services.PostService;
import com.bisblog.bisblog.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    // @desc Get all posts
    // @route GET /api/posts
    // @access Public
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    // @desc Get a single post
    // @route GET /api/posts/{id}
    // @access Public
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id) {
        var post = postService.getPostById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    // @desc Create a post
    // @route POST /api/posts
    // @access Private
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest post, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var newPost =  postService.createPost(post, user);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    // @desc Update a post
    // @route PUT /api/posts/{id}
    // @access Private
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID id, @RequestBody PostRequest post, @AuthenticationPrincipal UserDetails userDetails) {
        var existingPost = postService.getPostById(id);
        var user = userService.findByEmail(userDetails.getUsername());

        if (existingPost.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        var updatedPost = postService.updatePost(id, post, user);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // @desc Delete a post
    // @route DELETE /api/posts/{id}
    // @access Private
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        boolean result = postService.deletePost(id, user);

        if (!result)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
