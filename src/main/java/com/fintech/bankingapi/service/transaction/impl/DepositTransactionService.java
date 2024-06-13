package com.fintech.bankingapi.service.transaction.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.transaction.TransactionService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.fintech.bankingapi.enums.TransactionType.DEPOSIT;

@Service
public class DepositTransactionService extends TransactionService {

    @Override
    public TransactionDTO createTransaction(String accountNumber, @Nullable String targetAccountNumber, BigDecimal amount) {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return DEPOSIT;
    }
}
