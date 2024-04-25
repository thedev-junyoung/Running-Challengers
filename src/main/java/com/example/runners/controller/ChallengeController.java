package com.example.runners.controller;

import com.example.runners.entity.Challenge;
import com.example.runners.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/challenges")  // 모든 메소드를 /users 경로 아래로 이동
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    public ResponseEntity<Challenge> createChallenge(@RequestBody Challenge challenge) {
        System.out.println("createChallenge() in ChallengeController");
        Challenge newChallenge = challengeService.saveChallenge(challenge);
        return ResponseEntity.ok(newChallenge);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getChallenge(@PathVariable int id) {
        return challengeService.getChallengeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeService.getAllChallenges();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable int id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.ok().build();
    }
}
