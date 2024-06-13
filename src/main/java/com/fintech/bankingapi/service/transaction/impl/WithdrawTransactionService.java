package com.fintech.bankingapi.service.transaction.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.transaction.AbstractTransactionService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.fintech.bankingapi.enums.TransactionType.WITHDRAW;

@Service
public class WithdrawTransactionService extends AbstractTransactionService {

    @Override
    public TransactionDTO createTransaction(String accountNumber, @Nullable String targetAccountNumber, BigDecimal amount) {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return WITHDRAW;
    }
}
