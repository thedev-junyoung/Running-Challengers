package com.example.runners.dto.user;

import com.example.runners.type.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * DTO for {@link com.example.runners.entity.User}
 */
@Data
public class UserDTO implements Serializable {
    int id;
    String email;
    String username;
    Role role;
    String profileMsg;
    String profileImg;
    Date createdAt;
    Set<ChallengeUserDTO> challenges;
}