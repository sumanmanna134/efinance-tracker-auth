/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.exception;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.utils.ErrorHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex){
//        return ErrorHelper.getErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ApiResponse<Object>> handleRefreshTokenException(RefreshTokenExpiredException ex){
        return ErrorHelper.getErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNameNotFoundException(UsernameNotFoundException ex){
        return ErrorHelper.getErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistException(UserAlreadyExistException ex){
        return ErrorHelper.getErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex){
        return ErrorHelper.getErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateKeyException(RuntimeException ex) {
        ApiResponse<Object> response = ApiResponse.error(extractMessage(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String extractMessage(RuntimeException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("duplicate key error")) {
            if (message.contains("user_id") || message.contains("userId")) {
                return "User ID already exists";
            } else if (message.contains("email")) {
                return "Email already exists";
            } else if (message.contains("phone")) {
                return "Phone number already exists";
            }
        }
        return message;
    }




}
