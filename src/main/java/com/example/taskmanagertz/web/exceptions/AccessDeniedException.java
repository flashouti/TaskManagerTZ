package com.example.taskmanagertz.web.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccessDeniedException extends RuntimeException{
    private String code = HttpStatus.FORBIDDEN.toString();
    public AccessDeniedException(String message){
        super(message);
    }
}
