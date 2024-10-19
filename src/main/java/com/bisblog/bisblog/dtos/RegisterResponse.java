package com.bisblog.bisblog.dtos;

import com.bisblog.bisblog.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
