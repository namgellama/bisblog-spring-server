package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.CommentNotFoundException;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.repositories.CommentRepository;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentResponse createComment(UUID postId, CommentRequest comment, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found."));

        var newComment = Comment.builder()
                .description(comment.getDescription())
                .post(post)
                .user(user)
                .build();

        return modelMapper.map(commentRepository.save(newComment), CommentResponse.class);
    }

    @Override
    public CommentResponse createCommentReply(UUID postId, UUID commentId, CommentRequest comment, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found."));
        var commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));

        var newComment = Comment.builder()
                .description(comment.getDescription())
                .post(post)
                .user(user)
                .parentComment(commentEntity)
                .build();

        return modelMapper.map(commentRepository.save(newComment), CommentResponse.class);
    }
}
