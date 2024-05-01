package com.example.runners;


import com.example.runners.controller.UserController;
import com.example.runners.dto.user.JoinRequest;
import com.example.runners.dto.user.UpdateUserRequest;
import com.example.runners.entity.User;
import com.example.runners.service.JoinService;
import com.example.runners.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JoinService joinService;

    @MockBean
    private UserService userService;

    @Test
    public void testJoinPost() throws Exception {
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"testpass\"}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("join complete!"));

        verify(joinService).join(any(JoinRequest.class));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        given(userService.getUserById(anyInt())).willReturn(Optional.empty());

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setEmail("newemail@test.com");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setEmail("newemail@test.com");

        given(userService.updateUser(eq(1), any(UpdateUserRequest.class))).willReturn(Optional.of(updatedUser));

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"newemail@test.com\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@test.com"));
    }
}