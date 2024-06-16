package com.example.runners.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "challenge_goal")
public class ChallengeGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id", nullable = false)
    private Long goalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(name = "goal_type", nullable = false)
    private String goalType; // 예: 'distance', 'time', 'steps'

    @Column(name = "goal_value", nullable = false)
    private Float goalValue; // 예: 50.0 (km), 10.0 (hours)

    @Column(name = "unit", nullable = false)
    private String unit; // 예: 'km', 'hours'
}