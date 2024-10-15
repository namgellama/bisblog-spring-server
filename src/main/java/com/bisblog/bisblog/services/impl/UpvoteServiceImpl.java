package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.CommentNotFoundException;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.repositories.CommentRepository;
import com.bisblog.bisblog.repositories.DownvoteRepository;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.repositories.UpvoteRepository;
import com.bisblog.bisblog.services.UpvoteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpvoteServiceImpl implements UpvoteService {
    private final UpvoteRepository upvoteRepository;
    private final DownvoteRepository downvoteRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public UpvoteServiceImpl(UpvoteRepository upvoteRepository, DownvoteRepository downvoteRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.upvoteRepository = upvoteRepository;
        this.downvoteRepository = downvoteRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Upvote upvotePost(UUID postId, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found."));
        var existingUpvote = upvoteRepository.findByPostIdAndUserId(postId, user.getId());
        var existingDownvote = downvoteRepository.findByPostIdAndUserId(postId, user.getId());

        if (existingUpvote != null) {
            upvoteRepository.deleteById(existingUpvote.getId());
            return null;
        }

        if (existingDownvote != null) {
            downvoteRepository.deleteById(existingDownvote.getId());
        }

        var newUpvote = Upvote.builder()
                .post(post)
                .user(user)
                .build();

        return upvoteRepository.save(newUpvote);
    }

    @Override
    public Upvote upvoteComment(UUID commentId, User user) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));
        var existingUpvote = upvoteRepository.findByCommentIdAndUserId(commentId, user.getId());
        var existingDownvote = downvoteRepository.findByCommentIdAndUserId(commentId, user.getId());
        System.out.println(comment);
        if (existingUpvote != null) {
            upvoteRepository.deleteById(existingUpvote.getId());
            return null;
        }

        if (existingDownvote != null) {
            downvoteRepository.deleteById(existingDownvote.getId());
        }

        var newUpvote = Upvote.builder()
                .comment(comment)
                .user(user)
                .build();

        return upvoteRepository.save(newUpvote);
    }
}
