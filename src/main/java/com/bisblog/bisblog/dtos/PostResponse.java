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
public class PostResponse {
    private UUID id;
    private String title;
    private String body;
    private AuthorResponse author;
    private long upvoteCount;
    private long downvoteCount;
    private long voteCount;

    private long commentCount;
}
