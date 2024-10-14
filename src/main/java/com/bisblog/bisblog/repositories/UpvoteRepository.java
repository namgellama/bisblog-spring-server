package com.bisblog.bisblog.repositories;

import com.bisblog.bisblog.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, UUID> {
    Upvote findByPost_IdAndUser_Id(UUID postId, UUID userId);
    long countByPost_Id(UUID postId);
}
