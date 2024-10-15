package com.bisblog.bisblog.services.impl;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.entities.User;
import com.bisblog.bisblog.exceptions.CommentNotFoundException;
import com.bisblog.bisblog.exceptions.NestedCommentException;
import com.bisblog.bisblog.exceptions.PostNotFoundException;
import com.bisblog.bisblog.repositories.CommentRepository;
import com.bisblog.bisblog.repositories.DownvoteRepository;
import com.bisblog.bisblog.repositories.PostRepository;
import com.bisblog.bisblog.repositories.UpvoteRepository;
import com.bisblog.bisblog.services.CommentService;
import com.bisblog.bisblog.services.DownvoteService;
import com.bisblog.bisblog.services.UpvoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UpvoteRepository upvoteRepository;
    private final DownvoteRepository downvoteRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UpvoteRepository upvoteRepository,
            DownvoteRepository downvoteRepository,
            ModelMapper modelMapper
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.upvoteRepository = upvoteRepository;
        this.downvoteRepository = downvoteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentResponse> getAllComments(UUID postId) {
        boolean exists = postRepository.existsById(postId);

        if (!exists)
            throw new PostNotFoundException("Post not found.");

        return commentRepository.findByPostId(postId)
                .stream()
                .map((commentEntity) -> {
                    var comments = modelMapper.map(commentEntity, CommentResponse.class);
                    long upvoteCount = upvoteRepository.countByCommentId(commentEntity.getId());
                    long downvoteCount = downvoteRepository.countByCommentId(commentEntity.getId());
                    long voteCount = upvoteCount - downvoteCount;

                    comments.setUpvoteCount(upvoteCount);
                    comments.setDownvoteCount(downvoteCount);
                    comments.setVoteCount(voteCount);
                    return comments;
                })
                .collect(Collectors.toList());
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
    public CommentResponse createCommentReply(UUID commentId, CommentRequest comment, User user)  {
        var commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));
        var post = commentEntity.getPost();


        if (commentEntity.getParentComment() != null) {
            throw new NestedCommentException("Deep nested comment is not allowed");
        }

        var newComment = Comment.builder()
                .description(comment.getDescription())
                .post(post)
                .user(user)
                .parentComment(commentEntity)
                .build();

        return modelMapper.map(commentRepository.save(newComment), CommentResponse.class);
    }

    @Override
    public List<CommentResponse> getAllRepliesByCommentId(UUID commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));


        return commentRepository.findByParentCommentId(commentId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteComment(UUID commentId, User user) {
        var comment = commentRepository.findById(commentId);


        if (comment == null || comment.get().getUser().getId() != user.getId()) {
            return false;
        }

        commentRepository.deleteById(commentId);
        return true;
    }
}
