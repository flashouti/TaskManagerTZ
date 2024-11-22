package com.example.taskmanagertz.domain.task;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    public static TaskStatus ofString(String input){
        for(TaskStatus status : values()){
            if(status.toString().equals(input)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid task priority");
    }
}
