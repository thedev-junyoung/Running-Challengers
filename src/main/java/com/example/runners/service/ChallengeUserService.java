package com.example.runners.service;

import com.example.runners.dto.user.ChallengeUserDTO;
import com.example.runners.entity.Challenge;
import com.example.runners.entity.ChallengeUser;
import com.example.runners.entity.User;
import com.example.runners.repository.ChallengeUserRepository;
import com.example.runners.repository.ChallengeRepository;
import com.example.runners.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChallengeUserService {

    private final ChallengeUserRepository participantRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ChallengeUserService(ChallengeUserRepository participantRepository,
                                ChallengeRepository challengeRepository,
                                UserRepository userRepository,
                                ModelMapper modelMapper) {
        this.participantRepository = participantRepository;
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        // ChallengeParticipant 객체에서 ChallengeParticipantDTO로 매핑
        modelMapper.typeMap(ChallengeUser.class, ChallengeUserDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getChallenge().getId(), ChallengeUserDTO::setChallengeId);
            mapper.map(src -> src.getUser().getUserId(), ChallengeUserDTO::setUserId);
        });
    }
    // 참가를 추가하는 메서드
    public ChallengeUserDTO addParticipant(int challengeId, int userId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found with ID: " + challengeId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // 기존에 이미 참여했는지 확인
        Optional<ChallengeUser> existing = participantRepository.findByChallengeIdAndUserId(challengeId, userId);
        if (existing.isPresent()) {
            return modelMapper.map(existing.get(), ChallengeUserDTO.class);
        }

        ChallengeUser participant = new ChallengeUser();
        participant.setChallenge(challenge);
        participant.setUser(user);
        participant = participantRepository.save(participant);

        return modelMapper.map(participant, ChallengeUserDTO.class);
    }

    // 참가를 취소하는 메서드
    public void removeParticipant(int challengeId, int userId) {
        ChallengeUser participant = participantRepository.findByChallengeIdAndUserId(challengeId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found for challenge ID: " + challengeId + " and user ID: " + userId));

        participantRepository.delete(participant);
    }

    public List<ChallengeUserDTO> getParticipants(int challengeId) {
        return participantRepository.findByChallengeId(challengeId).stream().map((element) -> modelMapper.map(element, ChallengeUserDTO.class)).collect(Collectors.toList());
    }
}
