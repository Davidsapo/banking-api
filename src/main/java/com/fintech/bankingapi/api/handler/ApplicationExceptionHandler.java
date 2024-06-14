package com.fintech.bankingapi.api.handler;

import com.fintech.bankingapi.exceptions.CustomExceptionBody;
import com.fintech.bankingapi.exceptions.CustomUncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomUncheckedException.class)
    public ResponseEntity<CustomExceptionBody> handleCustomException(CustomUncheckedException e) {
        log.warn("Custom exception occurred: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomExceptionBody> handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception occurred: {}", e.getMessage(), e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new CustomExceptionBody(e.getMessage(), "INTERNAL_SERVER_ERROR"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionBody> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation exception occurred: {}", ex.getMessage());
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .badRequest()
                .body(new CustomExceptionBody("Validation failed", "VALIDATION_FAILED", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomExceptionBody> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Http message not readable exception occurred: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CustomExceptionBody("Http message not readable", "HTTP_MESSAGE_NOT_READABLE"));
    }
}
