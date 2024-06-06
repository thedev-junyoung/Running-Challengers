package com.example.runners.dto.challengeuser;

import com.example.runners.entity.ChallengeUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link ChallengeUser}
 */
@Data
public class ChallengeUserDTO implements Serializable {
    int id;
    private int challengeId;
    private int userId;
    int progress;
    Date startDate;
    Date completionDate;
}