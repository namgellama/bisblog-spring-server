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
public class AuthorResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
