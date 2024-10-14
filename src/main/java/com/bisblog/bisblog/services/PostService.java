package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.PostRequest;
import com.bisblog.bisblog.dtos.PostResponse;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {
    List<PostResponse> getAllPosts();
    Optional<PostResponse> getPostById(UUID id);
    PostResponse createPost(PostRequest post, User user);
    PostResponse updatePost(UUID id, PostRequest post, User user);
    boolean deletePost(UUID id, User user);
}
