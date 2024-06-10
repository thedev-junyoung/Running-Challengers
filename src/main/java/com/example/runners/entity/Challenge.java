package com.example.runners.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id", nullable = false)
    private Long challengeId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 유저와 N:M 관계 - 여러 유저가 여러 챌린지에 참여할 수 있음.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "challenge_user", //중간 테이블(교차 테이블)을 명시
            joinColumns = @JoinColumn(name = "challenge_id"), //  현재 엔터티의 외래 키
            inverseJoinColumns = @JoinColumn(name = "user_id")) // 반대쪽 엔터티의 외래 키
    private Set<User> challengeUsers;

    // 챌린지 목표 - 복합 목표를 위한 1:N 관계
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChallengeGoal> goals;

    // 챌린지와 댓글의 관계
    //@OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    //private Set<ChallengeReply> challengeReplies;

    // 챌린지를 생성한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
