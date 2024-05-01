package com.example.runners.dto.user;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.example.runners.entity.ChallengeParticipant}
 */
@Data
public class ChallengeParticipantDTO implements Serializable {
    int id;
    private int challengeId;
    private int userId;
    int progress;
    Date startDate;
    Date completionDate;
}