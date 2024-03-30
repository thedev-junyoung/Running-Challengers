package com.example.runners.controller;

import com.example.runners.dto.JoinRequest;
import com.example.runners.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }

//    @ResponseBody
    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest){
        joinService.join(joinRequest);

        return "join complete!";
    }


}
