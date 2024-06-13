package com.fintech.bankingapi.api.handler;

import com.fintech.bankingapi.exceptions.CustomExceptionBody;
import com.fintech.bankingapi.exceptions.CustomUncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Exception handler
 *
 * @author David Sapozhnik
 */
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
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomExceptionBody(e.getMessage(), "INTERNAL_SERVER_ERROR"));
    }

    //TODO: Add more exception handlers
}
