package com.example.taskmanagertz.services;

import com.example.taskmanagertz.domain.comment.Comment;
import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.domain.task.TaskPriority;
import com.example.taskmanagertz.domain.task.TaskStatus;
import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.repository.TaskRepository;
import com.example.taskmanagertz.repository.UserRepository;
import com.example.taskmanagertz.utils.TaskMapper;
import com.example.taskmanagertz.web.dto.requests.task.*;
import com.example.taskmanagertz.web.dto.responses.comment.CommentResponse;
import com.example.taskmanagertz.web.dto.responses.task.TaskCreateResponse;
import com.example.taskmanagertz.web.dto.responses.task.TaskFullResponse;
import com.example.taskmanagertz.web.exceptions.AccessDeniedException;
import com.example.taskmanagertz.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Task findById(Long taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        return foundTask.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task does not exist"));
    }

    public List<TaskFullResponse> findByAuthorEmail(String email) {
        List<TaskFullResponse> taskFullResponses = new ArrayList<>();
        List<Task> tasks = taskRepository.findByAuthorEmail(email);
        for (Task task : tasks) {
            List<CommentResponse> commentResponses = new ArrayList<>();
            for (Comment comment : task.getComments()) {
                commentResponses.add(new CommentResponse(comment.getText()));
            }
            taskFullResponses.add(new TaskFullResponse(task.getId(), task.getTitle(), task.getDescription(),
                    task.getStatus().name(), task.getPriority().name(), task.getAuthor().getEmail(),  task.getAssignee().getEmail(), commentResponses));
        }
        return taskFullResponses;
    }

    public List<TaskFullResponse> findByAssigneeEmail(String email){
        List<TaskFullResponse> taskFullResponses = new ArrayList<>();
        List<Task> tasks = taskRepository.findByAssigneeEmail(email);
        for (Task task : tasks) {
            List<CommentResponse> commentResponses = new ArrayList<>();
            for (Comment comment : task.getComments()) {
                commentResponses.add(new CommentResponse(comment.getText()));
            }
            taskFullResponses.add(new TaskFullResponse(task.getId(), task.getTitle(), task.getDescription(),
                    task.getStatus().name(), task.getPriority().name(), task.getAuthor().getEmail(), task.getAssignee().getEmail(), commentResponses));
        }
        return taskFullResponses;
    }

    @Transactional
    public TaskCreateResponse create(TaskCreateRequest taskCreateRequest, User user) {
        Task task = new Task(taskCreateRequest.title(), taskCreateRequest.description(), user);
        taskRepository.save(task);
        return new TaskCreateResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().name(),
                task.getPriority().name(), task.getAuthor().getEmail());
    }

    @Transactional
    public TaskFullResponse updateTaskAssignee(TaskUpdateAssigneeRequest request, User author) {
        if (!validateAuthorAccess(author, request.taskId()))
            throw new AccessDeniedException("Access denied!");

        Optional<User> optionalUser = userRepository.findByEmail(request.email());
        User userToUpdate;
        if (optionalUser.isEmpty())
            throw new NotFoundException("User not found!");
        else userToUpdate = optionalUser.get();

        Optional<Task> task = taskRepository.findById(request.taskId());
        Task taskToUpdate;
        if (task.isPresent()) {
            taskToUpdate = task.get();
            taskToUpdate.setAssignee(userToUpdate);
            taskRepository.save(taskToUpdate);
        } else {
            throw new NotFoundException("Task not found!");
        }

        return taskMapper.toTaskFullResponse(taskToUpdate);
    }

    @Transactional
    public TaskFullResponse updateTask(TaskDTO taskDTO, User user) {
        Optional<Task> task = taskRepository.findById(taskDTO.id());
        Task taskToUpdate;
        if (task.isPresent()) {
            taskToUpdate = task.get();
            taskToUpdate.setTitle(taskDTO.title());
            taskToUpdate.setDescription(taskDTO.description());
            taskRepository.save(taskToUpdate);
        } else {
            throw new NotFoundException("Task not found!");
        }

        return taskMapper.toTaskFullResponse(taskToUpdate);
    }


    @Transactional
    public TaskFullResponse updateTaskStatus (TaskStatusUpdateRequest request, User user) {
        Long taskId = request.id();
        String status = request.status();
        if (!validateAuthorAccess(user, taskId) && !validateAssigneeAccess(user, taskId))
            throw new AccessDeniedException("Access denied!");

        Optional<Task> task = taskRepository.findById(taskId);
        Task taskToUpdate;
        if (task.isPresent()) {
            taskToUpdate = task.get();
            taskToUpdate.setStatus(TaskStatus.ofString(status));
            taskRepository.save(taskToUpdate);
        } else {
            throw new NotFoundException("Task not found!");
        }

        return taskMapper.toTaskFullResponse(taskToUpdate);
    }

    @Transactional
    public TaskFullResponse updateTaskPriority(TaskPriorityUpdateRequest request, User user) {
        Long taskId = request.id();
        String priority = request.priority();
        if (!validateAuthorAccess(user, taskId)) throw new AccessDeniedException("Access denied!");
        Optional<Task> task = taskRepository.findById(taskId);
        Task taskToUpdate;
        if (task.isPresent()) {
            taskToUpdate = task.get();
            taskToUpdate.setPriority(TaskPriority.ofString(priority));
            taskRepository.save(taskToUpdate);
        } else {
            throw new NotFoundException("Task not found!");
        }

        return taskMapper.toTaskFullResponse(taskToUpdate);
    }

    @Transactional
    public void delete(User user, Long taskId) {
        if (!validateAuthorAccess(user, taskId))
            throw new AccessDeniedException("You do not have permission to delete this task");
        taskRepository.deleteById(taskId);
    }

    public boolean validateAssigneeAccess(User user, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            return task.get().getAssignee().getId().equals(user.getId());
        }
        return false;
    }

    public boolean validateAuthorAccess(User user, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            return task.get().getAuthor().getId().equals(user.getId());
        }
        return false;
    }
}
