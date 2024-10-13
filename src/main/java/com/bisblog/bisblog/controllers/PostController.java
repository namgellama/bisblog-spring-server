package com.bisblog.bisblog.controllers;

import com.bisblog.bisblog.dtos.PostDTO;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return postService.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + id));
    }

    @PostMapping
    public Post createPost(@RequestBody PostDTO post) {
        return postService.createPost(post);
    }
}
