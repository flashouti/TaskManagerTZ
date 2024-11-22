package com.example.taskmanagertz.services;

import com.example.taskmanagertz.domain.comment.Comment;
import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.repository.CommentRepository;
import com.example.taskmanagertz.repository.TaskRepository;
import com.example.taskmanagertz.web.dto.requests.comment.CommentCreateRequest;
import com.example.taskmanagertz.web.dto.responses.comment.CommentFullResponse;
import com.example.taskmanagertz.web.exceptions.AccessDeniedException;
import com.example.taskmanagertz.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    CommentRepository commentRepository;
    TaskRepository taskRepository;
    TaskService taskService;

    @Transactional
    public CommentFullResponse addComment(CommentCreateRequest request, User user) {
        Long taskId = request.taskId();
        Comment comment = new Comment();
        if (!taskService.validateAuthorAccess(user, taskId) && !taskService.validateAssigneeAccess(user, taskId))
            throw new AccessDeniedException("You do not have permission to add a comment");
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            comment.setTask(task);
            comment.setAuthor(user);
            comment.setText(request.text());
            commentRepository.save(comment);
            return new CommentFullResponse(comment.getId(), user.getId(), comment.getText(), user.getEmail());
        } else {
            throw new NotFoundException("Task not found");
        }
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        Task task;
        if (comment.isPresent()) {
            task = comment.get().getTask();
        } else {
            throw new NotFoundException("Comment not found");
        }
        if (!taskService.validateAuthorAccess(user, task.getId()) )
            throw new AccessDeniedException("You do not have permission to delete a comment");

        commentRepository.deleteById(commentId);
    }
}
