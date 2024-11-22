package com.example.taskmanagertz.utils;

import com.example.taskmanagertz.domain.comment.Comment;
import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.web.dto.responses.comment.CommentResponse;
import com.example.taskmanagertz.web.dto.responses.task.TaskFullResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {
    public TaskFullResponse toTaskFullResponse(Task task) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : task.getComments()) {
            commentResponses.add(new CommentResponse(comment.getText()));
        }

        return new TaskFullResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getAuthor().getEmail(),
                task.getAssignee().getEmail(),
                commentResponses
        );
    }
}
