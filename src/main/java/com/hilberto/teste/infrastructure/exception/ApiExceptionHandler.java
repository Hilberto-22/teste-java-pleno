package com.hilberto.teste.infrastructure.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public org.springframework.http.ResponseEntity<ApiExceptionResponse> handleApiException(ApiException ex) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                ex.getMessage(),
                ex.getStatus(),
                ex.getDetails()
        );
        return new org.springframework.http.ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<ApiExceptionResponse> handleGeneralException(Exception ex) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                "An unexpected error occurred",
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
        return new org.springframework.http.ResponseEntity<>(response, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
