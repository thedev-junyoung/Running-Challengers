package com.example.runners.repository;

import com.example.runners.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
}
