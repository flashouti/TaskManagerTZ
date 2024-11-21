package com.example.taskmanagertz.web.controller;

import com.example.taskmanagertz.services.AuthenticationService;
import com.example.taskmanagertz.web.dto.requests.auth.AuthenticationRequest;
import com.example.taskmanagertz.web.dto.requests.auth.RegistrationRequest;
import com.example.taskmanagertz.web.dto.responses.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/auth")
    public AuthenticationResponse createAuthToken(@RequestBody AuthenticationRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/registration")
    public AuthenticationResponse createNewUser(@RequestBody RegistrationRequest registrationUserDto) {
        return authService.register(registrationUserDto);
    }
}
