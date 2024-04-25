package com.example.runners.repository;

import com.example.runners.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
}