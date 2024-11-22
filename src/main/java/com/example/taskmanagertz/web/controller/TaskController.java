package com.example.taskmanagertz.web.controller;

import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.services.TaskService;
import com.example.taskmanagertz.web.dto.requests.task.*;
import com.example.taskmanagertz.web.dto.responses.task.TaskCreateResponse;
import com.example.taskmanagertz.web.dto.responses.task.TaskFullResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskManager/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/createdTasks")
    public List<TaskFullResponse> getCreatedTasks(@AuthenticationPrincipal User user){
        return taskService.findByAuthorEmail(user.getEmail());
    }

    @GetMapping("/editedTasks")
    public List<TaskFullResponse> getEditedTasks(@AuthenticationPrincipal User user){
        return taskService.findByAssigneeEmail(user.getEmail());
    }


    @PostMapping
    public TaskCreateResponse createTask(@AuthenticationPrincipal User user,
                                         @RequestBody @Valid TaskCreateRequest taskCreateRequest){
        return taskService.create(taskCreateRequest, user);
    }

    @PutMapping
    public TaskFullResponse updateTask(@AuthenticationPrincipal User user,
                                         @RequestBody @Valid TaskDTO taskDto){

        return taskService.updateTask(taskDto, user);
    }

    @PatchMapping("/assignee")
    public TaskFullResponse updateTaskAssignee(@AuthenticationPrincipal User user,
                                               @RequestBody @Valid TaskUpdateAssigneeRequest taskUpdateAssigneeRequest){
        return taskService.updateTaskAssignee(taskUpdateAssigneeRequest, user);
    }

    @PatchMapping("/status")
    public TaskFullResponse updateTaskStatus(@AuthenticationPrincipal User user,
                                             @RequestBody @Valid TaskStatusUpdateRequest request){
        return taskService.updateTaskStatus(request, user);
    }

    @PatchMapping("/priority")
    public TaskFullResponse updateTaskPriority(@AuthenticationPrincipal User user,
                                               @RequestBody @Valid TaskPriorityUpdateRequest request){
        return taskService.updateTaskPriority(request, user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal User user,
                                           @PathVariable Long id){
        taskService.delete(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
