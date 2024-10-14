package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.repositories.UpvoteRepository;
import com.bisblog.bisblog.services.UpvoteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpvoteServiceImpl implements UpvoteService {
    private final UpvoteRepository upvoteRepository;
    private final PostRepository postRepository;

    public UpvoteServiceImpl(UpvoteRepository upvoteRepository, PostRepository postRepository) {
        this.upvoteRepository = upvoteRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Upvote upvote(UUID postId, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        var existingUpvote = upvoteRepository.findByPost_IdAndUser_Id(postId, user.getId());

        if (existingUpvote != null) {
            upvoteRepository.deleteById(existingUpvote.getId());
            return null;
        }

        var newUpvote = Upvote.builder()
                .post(post)
                .user(user)
                .build();

        return upvoteRepository.save(newUpvote);
    }
}
