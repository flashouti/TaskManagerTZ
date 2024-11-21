package com.example.taskmanagertz.web.dto.requests.task;

import com.example.taskmanagertz.domain.task.TaskStatus;
import jakarta.validation.constraints.*;

public record UpdateTaskRequest (@NotBlank @Size(min = 5, max = 30, message = "Название задачи не может быть меньше 5 или больше 30 символов") String title,
                                 @NotBlank @Size(min = 5, max = 100, message = "Описание задачи не может быть меньше 5 или больше 100 символов") String description,
                                 @NotBlank TaskStatus status) {
}
