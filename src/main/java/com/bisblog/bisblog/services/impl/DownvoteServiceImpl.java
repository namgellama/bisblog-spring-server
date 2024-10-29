package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.DownvoteResponse;
import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.CommentNotFoundException;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.repositories.CommentRepository;
import com.bisblog.bisblog.repositories.DownvoteRepository;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.repositories.UpvoteRepository;
import com.bisblog.bisblog.services.DownvoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class DownvoteServiceImpl implements DownvoteService {
    private final UpvoteRepository upvoteRepository;
    private final DownvoteRepository downvoteRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(DownvoteServiceImpl.class);


    public DownvoteServiceImpl(UpvoteRepository upvoteRepository, DownvoteRepository downvoteRepository, PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.upvoteRepository = upvoteRepository;
        this.downvoteRepository = downvoteRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    // Create a post downvote
    @Override
    public DownvoteResponse downvotePost(UUID postId, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found."));
        var existingDownvote = downvoteRepository.findByPostIdAndUserId(postId, user.getId());
        var existingUpvote = upvoteRepository.findByPostIdAndUserId(postId, user.getId());

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

        return modelMapper.map(downvoteRepository.save(newDownvote), DownvoteResponse.class);
    }

    // Create a comment downvote
    @Override
    public DownvoteResponse downvoteComment(UUID commentId, User user) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));
        var existingDownvote = downvoteRepository.findByCommentIdAndUserId(commentId, user.getId());
        var existingUpvote = upvoteRepository.findByCommentIdAndUserId(commentId, user.getId());

        if (existingDownvote != null) {
            downvoteRepository.deleteById(existingDownvote.getId());
            return null;
        }

        if (existingUpvote != null) {
            upvoteRepository.deleteById(existingUpvote.getId());
        }

        var newDownvote = Downvote.builder()
                .comment(comment)
                .user(user)
                .build();

        return modelMapper.map(downvoteRepository.save(newDownvote), DownvoteResponse.class);
    }
}
