package com.shubham.saas.error.handler;

import com.shubham.saas.error.dto.ApiError;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex){
        log.error("Unhandled Exception: ", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex){
        log.warn("Validation Error: {}", ex.getMessage());

        Map<String, String> errorDetails = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errorDetails.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", errorDetails);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex){
        log.warn("Access Denied: ", ex);
        return buildResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex){
        log.warn("Authentication Error: ", ex);
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex){
        log.warn("Jwt Error: ", ex);
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message){
        return buildResponse(status, message, null);
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, Map<String, String> details){
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                details,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(apiError);
    }
}
