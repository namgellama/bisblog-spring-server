package com.bisblog.bisblog.repositories;

import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DownvoteRepository extends JpaRepository<Downvote, UUID> {
    Downvote findByPostIdAndUserId(UUID postId, UUID userId);
    Downvote findByCommentIdAndUserId(UUID commentId, UUID userId);
    long countByPostId(UUID postId);
    long countByCommentId(UUID postId);
}
