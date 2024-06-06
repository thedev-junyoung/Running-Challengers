package com.example.runners.repository;

import com.example.runners.entity.ChallengeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Integer> {
    List<ChallengeUser> findByChallengeId(int challengeId);
    @Query("SELECT p FROM ChallengeUser p WHERE p.challenge.id = :challengeId AND p.user.id = :userId")
    Optional<ChallengeUser> findByChallengeIdAndUserId(@Param("challengeId") int challengeId, @Param("userId") int userId);

}