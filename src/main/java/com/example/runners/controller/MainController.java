package com.example.runners.controller;

import com.example.runners.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {
    // 4. ERD, Function 짜오세요.

    @GetMapping("/")
    public String getMain() {
        /*// 1. 왜 얘는 잘못되었을까? HINT
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. 잘못된 걸 고치려면 어디를 어떻게 고쳐야할까?

        return "test page : " + name;*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "User is not authenticated";
        }
        String name = auth.getName();
        return "test page : " + name;
    }


}
