package com.example.runners.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ChallengeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    // 참가자의 진행 상황 (예: 달성한 걸음 수, 읽은 페이지 수 등)
    private int progress;

    // 참가자가 챌린지를 시작한 날짜
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    // 참가자가 챌린지를 완료한 날짜 (미완료시 null)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "completion_date")
    private Date completionDate;

    @PrePersist
    protected void onStart() {
        startDate = new Date();  // 참가자가 챌린지에 참여한 시점을 기록
    }
}