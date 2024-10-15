package com.bisblog.bisblog.repositories;

import com.bisblog.bisblog.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, UUID> {
    Upvote findByPostIdAndUserId(UUID postId, UUID userId);
    Upvote findByCommentIdAndUserId(UUID commentId, UUID userId);
    long countByPostId(UUID postId);
    long countByCommentId(UUID commentId);
}
