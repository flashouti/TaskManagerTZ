package com.example.taskmanagertz.web.dto.requests.task;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateRequest (@NotBlank String title, @NotBlank String description) {
}
