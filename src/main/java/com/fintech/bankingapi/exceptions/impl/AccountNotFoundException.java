package com.fintech.bankingapi.exceptions.impl;

import com.fintech.bankingapi.exceptions.CustomUncheckedException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class AccountNotFoundException extends CustomUncheckedException {

    private static final String MESSAGE = "Account not found";
    private static final String CODE = "ACCOUNT_NOT_FOUND";

    public AccountNotFoundException() {
        super(MESSAGE, CODE, HttpStatus.NOT_FOUND);
    }

    public AccountNotFoundException(Map<String, Object> additionalData) {
        super(MESSAGE, CODE, HttpStatus.NOT_FOUND, additionalData);
    }
}
