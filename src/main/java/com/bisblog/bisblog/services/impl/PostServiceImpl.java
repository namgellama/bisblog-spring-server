package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.PostDTO;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.respositories.PostRepository;
import com.bisblog.bisblog.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Post createPost(PostDTO post) {
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setBody(post.getBody());

        return postRepository.save(newPost);
    }
}
