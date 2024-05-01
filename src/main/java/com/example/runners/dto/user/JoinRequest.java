package com.example.runners.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JoinRequest {

    private String username;
    private String password;
}
