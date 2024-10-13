package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.PostDTO;
import com.bisblog.bisblog.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post createPost(PostDTO post);

}
