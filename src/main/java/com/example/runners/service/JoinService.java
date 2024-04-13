package com.example.runners.service;

import com.example.runners.dto.JoinRequest;
import com.example.runners.entity.UserEntity;
import com.example.runners.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder crypter){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = crypter;
    }

    public void join(JoinRequest joinRequest){

        if(userRepository.existsByUsername(joinRequest.getUsername())){
            return;
        }

        UserEntity user = new UserEntity();
        String password = joinRequest.getPassword();
        user.setUsername(joinRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole("ADMIN"); // 3. ADMIN / USER 권한 추가

        userRepository.save(user);
    }

}
