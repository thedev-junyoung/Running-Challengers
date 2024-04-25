package com.example.runners.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String role;
    private String email;
    @Temporal(TemporalType.TIMESTAMP) // Optional for clarity
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP) // Optional for clarity
    private Date updateAt;

    // 관계 설정 (User to Challenge)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Challenge> challenges = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.createAt = new Date(); // Set creation time to current date
    }
}
