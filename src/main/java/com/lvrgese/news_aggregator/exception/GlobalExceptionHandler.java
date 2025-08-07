package com.lvrgese.news_aggregator.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(Exception ex){
        return ResponseEntity.status(400).body(Map.of("Invalid Credentials",ex.getMessage()));
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public  ResponseEntity<?> handleInvalidJwtTokenException(Exception ex){
        return ResponseEntity.status(400).body(Map.of("Invalid Jwt Token",ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public  ResponseEntity<?> handleUsernameNotFoundException(Exception ex){
        return ResponseEntity.status(400).body(Map.of("Invalid username",ex.getMessage()));
    }
}
