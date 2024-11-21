package com.example.taskmanagertz.repository;

import com.example.taskmanagertz.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorEmail(String email);
    List<Task> findByAssigneeEmail(String email);
}
