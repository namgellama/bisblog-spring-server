package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.AuthorResponse;
import com.bisblog.bisblog.dtos.PostRequest;
import com.bisblog.bisblog.dtos.PostResponse;
import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.UnauthorizedException;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostResponse> getAllPosts() {
         return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostResponse.class))
                 .collect(Collectors.toList());
    }

    @Override
    public Optional<PostResponse> getPostById(UUID id) {
        return postRepository.findById(id)
                .map(postEntity -> modelMapper.map(postEntity, PostResponse.class));
    }

    @Override
    public PostResponse createPost(PostRequest post, User user) {
        Post postEntity = modelMapper.map(post, Post.class);
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity), PostResponse.class);
    }

    @Override
    public PostResponse updatePost(UUID id, PostRequest post, User user) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found."));

        if (!existingPost.getAuthor().getId().equals(user.getId())) {
            throw new UnauthorizedException("Not authorized.");
        }

        existingPost.setTitle(post.getTitle());
        existingPost.setBody(post.getBody());

        return modelMapper.map(postRepository.save(existingPost), PostResponse.class);
    }

    @Override
    public boolean deletePost(UUID id, User user) {
        var existingPost = postRepository.findById(id);
        boolean isPresent = postRepository.existsById(id);

        if (existingPost.isEmpty())
            return false;

        if (!existingPost.get().getAuthor().getId().equals(user.getId()))
            throw new UnauthorizedException("Not authorized.");

        postRepository.deleteById(id);
        return true;
    }
}
