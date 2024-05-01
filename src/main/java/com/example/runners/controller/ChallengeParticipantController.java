package com.example.runners.controller;


import com.example.runners.dto.user.ChallengeParticipantDTO;
import com.example.runners.service.ChallengeParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/challenges/{id}/participant")
public class ChallengeParticipantController {

    private final ChallengeParticipantService participantService;



    public ChallengeParticipantController(ChallengeParticipantService participantService) {
        this.participantService = participantService;
    }

    /**
     * 챌린지 참가
     * @param id 챌린지 ID
     * @param userId 참가자 ID
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> participateInChallenge(@PathVariable int id, @RequestParam int userId) {
        participantService.addParticipant(id, userId);
        return ResponseEntity.ok("Participant added successfully");
    }
    /**
     * 챌린지 취소
     * @param id 챌린지 ID
     * @param userId 참가자 ID
     * @return 성공 메시지
     */
    @DeleteMapping
    public ResponseEntity<String> cancelParticipation(@PathVariable int id, @RequestParam int userId) {
        participantService.removeParticipant(id, userId);
        return ResponseEntity.ok("Participant removed successfully");
    }
    /**
     * 챌린지의 모든 참가자 조회
     * @param id 챌린지 ID
     * @return 참가자 목록
     */
    @GetMapping("/list")
    public ResponseEntity<?> getParticipants(@PathVariable int id) {
        List<ChallengeParticipantDTO> participants = participantService.getParticipants(id);
        return ResponseEntity.ok(participants);
    }

}