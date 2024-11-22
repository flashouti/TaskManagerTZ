package com.example.taskmanagertz.web.dto.requests.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateRequest (@NotNull Long id, @NotBlank String status){
}
