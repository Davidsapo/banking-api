package com.fintech.bankingapi.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomExceptionBody {

    private final String message;
    private final String code;
    private final Map<String, Object> additionalData = new HashMap<>();

    public CustomExceptionBody(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public CustomExceptionBody(String message, String code, Map<String, Object> additionalData) {
        this.message = message;
        this.code = code;
        this.additionalData.putAll(additionalData);
    }
}
