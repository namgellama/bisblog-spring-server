package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.CommentRequest;
import com.bisblog.bisblog.dtos.CommentResponse;
import com.bisblog.bisblog.entities.Comment;
import com.bisblog.bisblog.entities.User;

import java.util.UUID;

public interface CommentService {
    CommentResponse createComment(UUID postId, CommentRequest comment, User user);
}
