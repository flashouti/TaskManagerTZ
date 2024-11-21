package com.example.taskmanagertz.web.dto.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest (@NotBlank
                                    @Email(message = "Username should be correct Email address.")
                                    String username,
                                    @NotBlank String password){
}
