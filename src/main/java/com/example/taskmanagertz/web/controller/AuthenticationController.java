package com.example.taskmanagertz.web.controller;

import com.example.taskmanagertz.services.AuthenticationService;
import com.example.taskmanagertz.web.dto.requests.auth.AuthenticationRequest;
import com.example.taskmanagertz.web.dto.requests.auth.RegistrationRequest;
import com.example.taskmanagertz.web.dto.responses.auth.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/taskManager/auth")
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/login")
    public AuthenticationResponse createAuthToken(@RequestBody @Valid  AuthenticationRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/registration")
    public AuthenticationResponse createNewUser(@RequestBody @Valid  RegistrationRequest registrationUserDto) {
        return authService.register(registrationUserDto);
    }
}
