package com.bisblog.bisblog.repositories;

import com.bisblog.bisblog.entities.Downvote;
import com.bisblog.bisblog.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DownvoteRepository extends JpaRepository<Downvote, UUID> {
    Downvote findByPost_IdAndUser_Id(UUID postId, UUID userId);
    long countByPost_Id(UUID postId);
}
