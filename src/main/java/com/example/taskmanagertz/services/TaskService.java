package com.example.taskmanagertz.services;

import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.repository.TaskRepository;
import com.example.taskmanagertz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task findById(Long taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        return foundTask.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task does not exist"));
    }

    public List<Task> findByAuthorEmail(String email) {
        return taskRepository.findByAuthorEmail(email);
    }

    public List<Task> findByAssigneeEmail(String email){
        return taskRepository.findByAssigneeEmail(email);
    }

    @Transactional
    public void save(Task task, User user) {
        task.setAuthor(user);
        task.setModified(LocalDateTime.now());
        taskRepository.save(task);
    }

    @Transactional
    public void setTaskAssignee(Long taskId, String email) {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findByEmail(email);
        if (task.isPresent() && user.isPresent()) {
            task.get().setAuthor(user.get());
        }
    }

    @Transactional
    public void updateTask(Task updatedTask, User user) {
        updatedTask.setModified(LocalDateTime.now());
        taskRepository.save(updatedTask);
    }

    @Transactional
    public void updateTaskAssignee(Long taskId, String email) {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findByEmail(email);
        if (task.isPresent() && user.isPresent()) {
            task.get().setModified(LocalDateTime.now());
        }
    }

    /*
    @Transactional
    public void updateTaskStatus (Long taskId, String status) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            task.get().getStatus(status);
        }
    }

    @Transactional
    public void updateTaskPriority(Long taskId, String priority) {

    }

     */

    @Transactional
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
