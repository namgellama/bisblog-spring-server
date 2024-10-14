package com.bisblog.bisblog.dtos;

import com.bisblog.bisblog.entities.Post;
import com.bisblog.bisblog.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpvoteRequest {
    private User user;
    private Post post;
}
