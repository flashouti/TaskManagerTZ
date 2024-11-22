package com.example.taskmanagertz.web.dto.requests.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest (@NotNull Long taskId, @NotBlank @Size (min = 2, max = 100, message = "Размер комментария должен быть от 2 до 100 символов") String text) {
}
