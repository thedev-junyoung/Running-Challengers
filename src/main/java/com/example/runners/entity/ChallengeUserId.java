package com.example.runners.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class ChallengeUserId implements Serializable {
    private Long challengeId;
    private Long userId;

    public ChallengeUserId() {}

    public ChallengeUserId(Long challengeId, Long userId) {
        this.challengeId = challengeId;
        this.userId = userId;
    }

    // equals()와 hashCode()를 반드시 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChallengeUserId that = (ChallengeUserId) o;
        return Objects.equals(challengeId, that.challengeId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(challengeId, userId);
    }
}
