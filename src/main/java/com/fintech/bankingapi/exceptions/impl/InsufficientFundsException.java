package com.fintech.bankingapi.exceptions.impl;

import com.fintech.bankingapi.exceptions.CustomUncheckedException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class InsufficientFundsException extends CustomUncheckedException {

    private static final String MESSAGE = "Insufficient funds";
    private static final String CODE = "INSUFFICIENT_FUNDS";

    public InsufficientFundsException() {
        super(MESSAGE, CODE, HttpStatus.NOT_FOUND);
    }

    public InsufficientFundsException(Map<String, Object> additionalData) {
        super(MESSAGE, CODE, HttpStatus.NOT_FOUND, additionalData);
    }
}
