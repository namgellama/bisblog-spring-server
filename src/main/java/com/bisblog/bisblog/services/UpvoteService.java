package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.UpvoteResponse;
import com.bisblog.bisblog.entities.User;

import java.util.UUID;

public interface UpvoteService {
    UpvoteResponse upvotePost(UUID postId, User user);
    UpvoteResponse upvoteComment(UUID commentId, User user);
}
