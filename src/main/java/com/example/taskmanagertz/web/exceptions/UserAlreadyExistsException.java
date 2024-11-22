package com.example.taskmanagertz.web.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private String code = HttpStatus.BAD_REQUEST.toString();
    public UserAlreadyExistsException(String message){
        super(message);
    }
}
