package com.hilberto.teste.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiExceptionResponse {
    private String message;
    private HttpStatus status;
    private String details;

    public ApiExceptionResponse(String message, HttpStatus status, String details) {
        this.message = message;
        this.status = status;
        this.details = details;
    }
}
