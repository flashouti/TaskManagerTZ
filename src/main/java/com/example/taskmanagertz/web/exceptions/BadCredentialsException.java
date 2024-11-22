package com.example.taskmanagertz.web.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadCredentialsException extends RuntimeException {
    private String code = HttpStatus.BAD_REQUEST.toString();
    public BadCredentialsException(String message){
        super(message);
    }
}
