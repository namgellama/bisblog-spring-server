package com.bisblog.bisblog.services;

import com.bisblog.bisblog.dtos.DownvoteResponse;
import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.entities.User;

import java.util.UUID;

public interface DownvoteService {
    DownvoteResponse downvotePost(UUID postId, User user);
    DownvoteResponse downvoteComment(UUID commentId, User user);
}
