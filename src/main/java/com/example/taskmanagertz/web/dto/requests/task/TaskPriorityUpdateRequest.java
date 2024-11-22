package com.example.taskmanagertz.web.dto.requests.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskPriorityUpdateRequest (@NotNull Long id, @NotBlank String priority) {
}
