package com.fintech.bankingapi.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountNumberGeneratorTest {

    private static final int ACCOUNT_NUMBER_LENGTH = 12;

    @Test
    public void testGenerateAccountNumber_Length() {
        String accountNumber = AccountNumberGenerator.generateAccountNumber();
        assertEquals(ACCOUNT_NUMBER_LENGTH, accountNumber.length(), "Account number should be 12 digits long");
    }

    @Test
    public void testGenerateAccountNumber_OnlyDigits() {
        String accountNumber = AccountNumberGenerator.generateAccountNumber();
        assertTrue(accountNumber.matches("\\d{12}"), "Account number should only contain digits");
    }

    @Test
    public void testGenerateAccountNumber_Unique() {
        String accountNumber1 = AccountNumberGenerator.generateAccountNumber();
        String accountNumber2 = AccountNumberGenerator.generateAccountNumber();
        assertNotEquals(accountNumber1, accountNumber2, "Account numbers should be unique");
    }
}
