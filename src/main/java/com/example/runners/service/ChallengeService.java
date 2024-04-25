package com.example.runners.service;

import com.example.runners.entity.Challenge;
import com.example.runners.repository.ChallengeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository){
        this.challengeRepository = challengeRepository;
    }

    public Challenge saveChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public Optional<Challenge> getChallengeById(int id) {
        return challengeRepository.findById(id);
    }

    public void deleteChallenge(int id) {
        challengeRepository.deleteById(id);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }
}
