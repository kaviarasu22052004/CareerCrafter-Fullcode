package com.hexaware.Career.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return buildError(ex, req, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAlreadyExists(AlreadyExistsException ex, HttpServletRequest req) {
        return buildError(ex, req, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest req) {
        return buildError(ex, req, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> buildError(Exception ex, HttpServletRequest req, HttpStatus status) {
        ApiError error = new ApiError();
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setPath(req.getRequestURI());
        return new ResponseEntity<>(error, status);
    }
}
