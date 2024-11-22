package com.example.taskmanagertz.web.dto.responses.task;

import com.example.taskmanagertz.web.dto.responses.comment.CommentResponse;

import java.util.List;

public record TaskFullResponse (Long id,
                                String title,
                                String description,
                                String status,
                                String priority,
                                String authorEmail,
                                String assigneeEmail,
                                List<CommentResponse> responseList) {
}
