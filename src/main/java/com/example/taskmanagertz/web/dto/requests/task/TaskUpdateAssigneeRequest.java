package com.example.taskmanagertz.web.dto.requests.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskUpdateAssigneeRequest (@NotNull Long taskId, @NotBlank String email) {
}
