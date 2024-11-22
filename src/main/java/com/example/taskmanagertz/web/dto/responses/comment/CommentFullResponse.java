package com.example.taskmanagertz.web.dto.responses.comment;

public record CommentFullResponse (Long commentId, Long taskId, String text, String authorEmail) {
}
