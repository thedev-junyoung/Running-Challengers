package com.example.runners.service;

import com.example.runners.dto.user.JoinRequest;
import com.example.runners.entity.User;
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
        System.out.println("join() in JoinService");
        if(userRepository.existsByUsername(joinRequest.getUsername())){
            System.out.println("existsByUsername: return in JoinService");
            return;
        }

        User user = new User();
        String password = joinRequest.getPassword();
        user.setUsername(joinRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole("ADMIN"); // 3. ADMIN / USER 권한 추가
        System.out.println("user:"+user);
        userRepository.save(user);
    }

}
