package com.example.taskmanagertz.web.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException{
    private String code = HttpStatus.BAD_REQUEST.toString();
    public BadRequestException(String message){
        super(message);
    }
}
