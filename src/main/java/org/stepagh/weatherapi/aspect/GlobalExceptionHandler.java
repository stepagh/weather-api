package org.stepagh.weatherapi.aspect;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.stepagh.weatherapi.exceptions.ExternalClientException;
import org.stepagh.weatherapi.exceptions.ExternalServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExternalClientException.class)
    public ResponseEntity<String> handleClientException(ExternalClientException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<String> handleServiceException(ExternalServiceException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}
