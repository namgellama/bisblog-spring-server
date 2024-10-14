package com.bisblog.bisblog.repositories;

import com.bisblog.bisblog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByPostId(UUID postId);
    List<Comment> findByParentCommentId(UUID commentId);
    long countByPostId(UUID postId);
}
