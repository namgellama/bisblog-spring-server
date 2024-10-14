package com.bisblog.bisblog.services;

import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.entities.Upvote;
import com.bisblog.bisblog.entities.User;

import java.util.UUID;

public interface UpvoteService {
    Upvote upvote(UUID postId, User user);
}
