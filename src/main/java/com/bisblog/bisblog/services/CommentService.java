package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.entities.User;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.UUID;

public interface CommentService {
    CommentResponse createComment(UUID postId, CommentRequest comment, User user);

    CommentResponse createCommentReply(UUID postId, UUID commentId, CommentRequest comment, User user) throws MethodNotAllowedException;
}
