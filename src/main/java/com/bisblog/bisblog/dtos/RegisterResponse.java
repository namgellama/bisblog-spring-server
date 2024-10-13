package com.bisblog.bisblog.dtos;

import com.bisblog.bisblog.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
