package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.entities.User;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<CommentResponse> getAllComments(UUID postId);
    CommentResponse createComment(UUID postId, CommentRequest comment, User user);
    CommentResponse createCommentReply(UUID commentId, CommentRequest comment, User user) throws MethodNotAllowedException;
    List<CommentResponse> getAllRepliesByCommentId(UUID commentId);

    boolean deleteComment(UUID commentId, User user);
}
