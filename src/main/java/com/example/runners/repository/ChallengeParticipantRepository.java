package com.example.runners.repository;

import com.example.runners.entity.ChallengeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Integer> {
    List<ChallengeParticipant> findByChallengeId(int challengeId);
    @Query("SELECT p FROM ChallengeParticipant p WHERE p.challenge.id = :challengeId AND p.user.id = :userId")
    Optional<ChallengeParticipant> findByChallengeIdAndUserId(@Param("challengeId") int challengeId, @Param("userId") int userId);

}