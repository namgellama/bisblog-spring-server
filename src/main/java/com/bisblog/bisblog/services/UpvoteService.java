package com.bisblog.bisblog.services;

import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.entities.User;

import java.util.UUID;

public interface UpvoteService {
    Upvote upvotePost(UUID postId, User user);
    Upvote upvoteComment(UUID commentId, User user);
}
