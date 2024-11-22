INSERT INTO user_info (id, email, password, role)
VALUES (1, 'user1@example.com', 'password1', 'USER'),(2, 'user2@example.com', 'password2', 'ADMIN');


INSERT INTO task (id, title, description, status, priority, modified, author_id, assignee_id)
VALUES (1, 'Task 1', 'Description for Task 1', 'PENDING', 'HIGH', NOW(), 1, 2), (2, 'Task 2', 'Description for Task 2', 'IN_PROGRESS', 'MEDIUM', NOW(), 1, NULL);

INSERT INTO comment (id, text, task_id, author_id) VALUES (1, 'Comment for Task 1', 1, 1), (2, 'Comment for Task 2', 2, 2);

