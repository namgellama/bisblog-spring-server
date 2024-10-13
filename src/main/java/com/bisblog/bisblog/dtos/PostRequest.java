package com.bisblog.bisblog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private UUID id;
    private String title;
    private String body;
}
