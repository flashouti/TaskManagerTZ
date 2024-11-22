package com.example.taskmanagertz.web.advice;

import com.example.taskmanagertz.web.dto.responses.error.BasicErrorResponse;
import com.example.taskmanagertz.web.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    static String VALIDATION_ERROR = "validation_error";

    @ExceptionHandler(value = BadRequestException.class)
    public BasicErrorResponse handleBadRequestException (BadRequestException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public BasicErrorResponse handleBadCredentialsException (BadCredentialsException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());

    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public BasicErrorResponse handleUserExistsException (UserAlreadyExistsException ex) {
       return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public BasicErrorResponse handleNotFoundException (NotFoundException ex){
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public BasicErrorResponse handleAccessDeniedException (AccessDeniedException ex){
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<BasicErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors().stream().map(fieldError ->
                        new BasicErrorResponse(VALIDATION_ERROR, HttpStatus.BAD_REQUEST.toString())).
                toList();
        return ResponseEntity.badRequest()
                .body(errors);
    }



}
