package com.example.runners.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        // 401 Unauthorized 응답을 반환
        return new ResponseEntity<>("Unauthorized: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
