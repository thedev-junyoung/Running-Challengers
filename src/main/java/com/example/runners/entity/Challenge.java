package com.example.runners.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String title;
    private String description;

    // 챌린지의 구체적인 수치 목표
    private int goalAmount;
    private String goalUnit; // 목표의 단위 (예: 걸음, 페이지, 시간)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="Asia/Seoul")
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "created_by") // 챌린지를 생성한 관리자
    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "challenge")
    private Set<ChallengeParticipant> participants; // 참가자


    // @PrePersist 및 @PreUpdate:
    // 이러한 주석은 엔터티가 유지되거나 업데이트되기 직전에 CreatedAt 및 updateAt 필드를 자동으로 설정하는 수명 주기 콜백 메서드에 사용
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }


}