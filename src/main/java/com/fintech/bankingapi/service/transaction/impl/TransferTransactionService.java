package com.fintech.bankingapi.service.transaction.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.transaction.AbstractTransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.fintech.bankingapi.enums.TransactionType.TRANSFER;

@Service
public class TransferTransactionService extends AbstractTransactionService {

    @Override
    public TransactionDTO createTransaction(String accountNumber, String targetAccountNumber, BigDecimal amount) {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return TRANSFER;
    }
}