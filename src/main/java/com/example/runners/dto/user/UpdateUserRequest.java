package com.example.runners.dto.user;

import lombok.Data;

@Data

public class UpdateUserRequest {
    private String username;
    private String email;
    private String role;
}
