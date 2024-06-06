package com.example.runners.service;

import com.example.runners.dto.challenge.ChallengeDTO;
import com.example.runners.entity.Challenge;
import com.example.runners.entity.User;
import com.example.runners.repository.ChallengeRepository;
import com.example.runners.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper; // 이 객체는 엔터티를 DTO로 매핑하는데 사용됩니다.
    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository, ModelMapper modelMapper){
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        configureModelMapper();
    }
    private void configureModelMapper() {
        // User 객체를 사용자 ID로 매핑
        modelMapper.typeMap(Challenge.class, ChallengeDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getCreatedBy().getUserId(), ChallengeDTO::setCreatedBy)
        );
    }
    public void saveChallenge(ChallengeDTO challengeDTO) {
        User user = userRepository.findById(challengeDTO.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found"));
        Challenge challenge = modelMapper.map(challengeDTO, Challenge.class);
        challenge.setCreatedBy(user);  // 사용자 엔티티 설정
        challengeRepository.save(challenge);
    }
    public Optional<ChallengeDTO> getChallengeById(int id) {
        return challengeRepository.findById(id)
                .map(challenge -> modelMapper.map(challenge, ChallengeDTO.class));
    }
    public List<ChallengeDTO> getAllChallenges() {
        return challengeRepository.findAll().stream()
                .map(challenge -> modelMapper.map(challenge, ChallengeDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<ChallengeDTO> updateChallenge(int id, ChallengeDTO challengeDTO) {
        return challengeRepository.findById(id).map(challenge -> {
            Optional.ofNullable(challengeDTO.getTitle()).ifPresent(challenge::setTitle);
            Optional.ofNullable(challengeDTO.getDescription()).ifPresent(challenge::setDescription);
            Optional.ofNullable(challengeDTO.getGoalUnit()).ifPresent(challenge::setGoalUnit);
            Optional.of(challengeDTO.getGoalAmount()).ifPresent(challenge::setGoalAmount);
            Optional.of(challengeDTO.getDeadline()).ifPresent(challenge::setDeadline);
            challenge = challengeRepository.save(challenge);
            return modelMapper.map(challenge, ChallengeDTO.class);  // 엔터티를 DTO로 변환
        });
    }

    public void deleteChallenge(int id) {
        challengeRepository.deleteById(id);
    }

}
