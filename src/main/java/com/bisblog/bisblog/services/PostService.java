package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.PostDTO;
import com.bisblog.bisblog.entities.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts();
    Optional<Post> getPostById(UUID id);
    Post createPost(PostDTO post);

}
