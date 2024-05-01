package com.example.runners.service;

import com.example.runners.dto.user.RunnerUserDetails;
import com.example.runners.dto.user.UpdateUserRequest;
import com.example.runners.entity.User;
import com.example.runners.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user != null){
            System.out.println("user !=null");
            return new RunnerUserDetails(user);
        }
        //return null;
        System.out.println("User not found with username");
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        System.out.println("getAllUsers()");
        return userRepository.findAll();
    }


    public Optional<User> updateUser(int id, UpdateUserRequest updateRequest) {
        return userRepository.findById(id).map(user -> {
            Optional.ofNullable(updateRequest.getUsername()).ifPresent(user::setUsername);
            Optional.ofNullable(updateRequest.getEmail()).ifPresent(user::setEmail);
            Optional.ofNullable(updateRequest.getRole()).ifPresent(user::setRole);
            return userRepository.save(user);
        });
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


}
