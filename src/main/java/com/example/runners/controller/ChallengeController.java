package com.example.runners.controller;

import com.example.runners.dto.challenge.ChallengeDTO;
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
    public ResponseEntity<String> createChallenge(@RequestBody ChallengeDTO challengeDTO) {
        challengeService.saveChallenge(challengeDTO);
        return ResponseEntity.ok("Challenge created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDTO> getChallenge(@PathVariable int id) {
        return challengeService.getChallengeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ChallengeDTO> getAllChallenges() {
        return challengeService.getAllChallenges();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDTO> updateChallenge(@PathVariable int id, @RequestBody ChallengeDTO challengeDTO) {
        return challengeService.updateChallenge(id, challengeDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable int id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.ok().build();
    }
}
