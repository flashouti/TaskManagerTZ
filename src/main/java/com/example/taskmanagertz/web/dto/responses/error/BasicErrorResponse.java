package com.example.taskmanagertz.web.dto.responses.error;

import java.time.LocalDateTime;

public record BasicErrorResponse(String code, String message, LocalDateTime timestamp) {
    public BasicErrorResponse(String code, String message) {
        this(code, message, LocalDateTime.now());
    }
}
