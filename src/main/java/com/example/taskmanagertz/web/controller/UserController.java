package com.example.taskmanagertz.web.controller;

import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.services.UserService;
import com.example.taskmanagertz.web.dto.responses.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskManager/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponse getUser(@AuthenticationPrincipal User user){
        return userService.getUser(user);
    }
}
