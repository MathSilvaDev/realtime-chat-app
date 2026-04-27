package com.matheus.realtimechat.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    private ResponseEntity<ApiError> toResponseEntity(HttpStatus status, Object message){
        return ResponseEntity
                .status(status)
                .body(new ApiError(status, message));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> illegalStateException(IllegalStateException ex){
        return toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> usernameNotFoundException(UsernameNotFoundException ex){
        ex.printStackTrace();
        return toResponseEntity(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidException(
            MethodArgumentNotValidException ex){

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                fieldError -> fieldError.getField(),
                                fieldError -> fieldError.getDefaultMessage(),
                                (ms1, ms2) -> ms1
                        )
                );

        return toResponseEntity(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex){
        ex.printStackTrace();
        return toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
    }
}
