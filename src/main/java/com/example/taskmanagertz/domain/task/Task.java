package com.example.taskmanagertz.domain.task;

import com.example.taskmanagertz.domain.comment.Comment;
import com.example.taskmanagertz.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDateTime modified;

    @ManyToOne
    private User author;

    @ManyToOne
    private User assignee;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment> comments;

    public Task(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.priority = TaskPriority.MEDIUM;
        this.modified = LocalDateTime.now();
        this.author = author;
        this.assignee = null;
        this.comments = null;
    }
}
