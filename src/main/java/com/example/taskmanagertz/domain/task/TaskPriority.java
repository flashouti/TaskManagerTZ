package com.example.taskmanagertz.domain.task;

public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH;

    public static TaskPriority ofString(String input){
        for(TaskPriority priority : values()){
            if(priority.toString().equals(input)){
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid task priority");
    }
}
