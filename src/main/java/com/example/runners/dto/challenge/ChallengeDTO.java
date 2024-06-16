package com.example.runners.dto.challenge;

import com.example.runners.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class ChallengeDTO {
    private int id;
    private String title;
    private String description;
    private int goalAmount;
    private String goalUnit;
    private long createdBy; // 작성자
    private Date deadline;
}
