package com.example.taskmanagertz.web.dto.responses.task;

public record TaskCreateResponse(Long id,
                                 String title,
                                 String description,
                                 String status,
                                 String priority,
                                 String authorEmail) {
}
