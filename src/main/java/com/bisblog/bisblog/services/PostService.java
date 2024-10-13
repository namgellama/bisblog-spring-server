package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.PostRequest;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts();
    Optional<Post> getPostById(UUID id);
    Post createPost(PostRequest post, User user);
    Post updatePost(UUID id, PostRequest post);
    boolean deletePost(UUID id);
}
