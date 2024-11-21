package com.example.taskmanagertz.services;

import com.example.taskmanagertz.domain.comment.Comment;
import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.repository.CommentRepository;
import com.example.taskmanagertz.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    private TaskRepository taskRepository;

    @Transactional
    public Comment addComment(Long taskId, Comment comment) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            comment.setTask(task);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    @Transactional
    public Comment updateComment(Long commentId, String newText) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setText(newText);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
