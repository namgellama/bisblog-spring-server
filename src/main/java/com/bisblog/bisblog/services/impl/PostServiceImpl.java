package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.services.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(UUID.randomUUID(), "test1", "test1"));
        posts.add(new Post(UUID.randomUUID(), "test2", "test2"));
        return posts;
    }
}
