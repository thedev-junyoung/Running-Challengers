package com.example.runners.dto.user;

import com.example.runners.entity.Role;
import lombok.Data;

@Data

public class UpdateUserRequest {
    private String username;
    private String password;
    private String profileImg;
    private String profileMsg;
}
