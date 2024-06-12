package com.fintech.bankingapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

@Getter
public class CustomUncheckedException extends RuntimeException {

    private final HttpStatusCode statusCode;
    private final CustomExceptionBody body;

    public CustomUncheckedException(String message, String code, HttpStatusCode statusCode) {
        super(message);
        this.body = new CustomExceptionBody(message, code);
        this.statusCode = statusCode;
    }

    public CustomUncheckedException(String message, String code, HttpStatusCode statusCode, Map<String, Object> additionalData) {
        super(message);
        this.statusCode = statusCode;
        this.body = new CustomExceptionBody(message, code, additionalData);
    }
}
