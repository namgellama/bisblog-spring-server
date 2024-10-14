package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.repositories.DownvoteRepository;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.repositories.UpvoteRepository;
import com.bisblog.bisblog.services.DownvoteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DownvoteServiceImpl implements DownvoteService {
    private final UpvoteRepository upvoteRepository;
    private final DownvoteRepository downvoteRepository;
    private final PostRepository postRepository;

    public DownvoteServiceImpl(UpvoteRepository upvoteRepository, DownvoteRepository downvoteRepository, PostRepository postRepository) {
        this.upvoteRepository = upvoteRepository;
        this.downvoteRepository = downvoteRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Downvote downvote(UUID postId, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        var existingDownvote = downvoteRepository.findByPostIdAndUserId(postId, user.getId());
        var existingUpvote = upvoteRepository.findByPost_IdAndUser_Id(postId, user.getId());

        if (existingDownvote != null) {
            downvoteRepository.deleteById(existingDownvote.getId());
            return null;
        }

        if (existingUpvote != null) {
            upvoteRepository.deleteById(existingUpvote.getId());
        }

        var newDownvote = Downvote.builder()
                .post(post)
                .user(user)
                .build();

        return downvoteRepository.save(newDownvote);
    }
}
