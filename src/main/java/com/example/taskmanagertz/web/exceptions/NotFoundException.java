package com.example.taskmanagertz.web.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException{
    private String code = HttpStatus.NOT_FOUND.toString();
    public NotFoundException(String message){
        super(message);
    }
}