package com.fintech.bankingapi.utils;

import java.util.Random;

public final class AccountNumberGenerator {

    private static final int ACCOUNT_NUMBER_LENGTH = 12;
    private static final Random RANDOM = new Random();

    private AccountNumberGenerator() {
    }

    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            accountNumber.append(RANDOM.nextInt(10));
        }
        return accountNumber.toString();
    }
}

