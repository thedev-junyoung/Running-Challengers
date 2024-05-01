package com.example.runners.dto.user;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.example.runners.entity.ChallengeParticipant}
 */
@Value
public class ChallengeParticipantDTO implements Serializable {
    int id;
    int progress;
    Date startDate;
    Date completionDate;
}