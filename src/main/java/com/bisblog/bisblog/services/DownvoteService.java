package com.bisblog.bisblog.services;

import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.entities.User;

import java.util.UUID;

public interface DownvoteService {
    Downvote downvotePost(UUID postId, User user);
    Downvote downvoteComment(UUID commentId, User user);
}
