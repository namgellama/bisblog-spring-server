package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.PostRequest;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(UUID id) {
        return postRepository.findById(id);
    }

    @Override
    public Post createPost(PostRequest post) {
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setBody(post.getBody());

        return postRepository.save(newPost);
    }

    @Override
    public Post updatePost(UUID id, PostRequest post) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with ID: " + id));

        existingPost.setTitle(post.getTitle());
        existingPost.setBody(post.getBody());

        return postRepository.save(existingPost);
    }

    @Override
    public boolean deletePost(UUID id) {
        boolean isPresent = postRepository.existsById(id);

        if (!isPresent)
            return false;

        postRepository.deleteById(id);
        return true;
    }
}
