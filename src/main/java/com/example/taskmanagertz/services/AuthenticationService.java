package com.example.taskmanagertz.services;

import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.domain.user.UserRole;
import com.example.taskmanagertz.repository.UserRepository;
import com.example.taskmanagertz.utils.JwtTokenUtils;
import com.example.taskmanagertz.web.dto.requests.auth.AuthenticationRequest;
import com.example.taskmanagertz.web.dto.requests.auth.RegistrationRequest;
import com.example.taskmanagertz.web.dto.responses.auth.AuthenticationResponse;
import com.example.taskmanagertz.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest registerRequest) {
        User user = User.
                builder()
                .email(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse (jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );
        Optional<User> user = userRepository.findByEmail(authenticationRequest.username());

        String jwtToken;
        if(user.isPresent()) {
            jwtToken = jwtService.generateToken(user.get());
        } else {
            throw new NotFoundException("Invalid username or password");
        }
        return new AuthenticationResponse (jwtToken);
    }

    public boolean userExists(String username){
        return userRepository.existsUserByEmail(username);
    }
}