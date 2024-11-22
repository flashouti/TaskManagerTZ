package com.example.taskmanagertz.domain.comment;

import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToOne
    private Task task;

    @ManyToOne
    private User author;

    public Comment(String text, Task task, User author) {
        this.text = text;
        this.task = task;
        this.author = author;
    }
}