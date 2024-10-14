package com.bisblog.bisblog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private UUID id;
    private String body;
    private UUID postId;
    private UUID userId;
    private UUID commentId;
}