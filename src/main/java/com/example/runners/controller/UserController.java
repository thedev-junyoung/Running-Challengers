package com.example.runners.controller;

import com.example.runners.dto.JoinRequest;
import com.example.runners.dto.UpdateUserRequest;
import com.example.runners.entity.User;
import com.example.runners.service.JoinService;
import com.example.runners.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {

    private final JoinService joinService;
    private final UserService userService;

    public UserController(JoinService joinService, UserService userService) {
        this.joinService = joinService;
        this.userService = userService;
    }

    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest){
        System.out.println("join() in UserController");
        joinService.join(joinRequest);
        return "join complete!";
    }
    @GetMapping("/join")
    public String join(){
        return "get join view";
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody UpdateUserRequest updateRequest) {
        return userService.updateUser(id, updateRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
