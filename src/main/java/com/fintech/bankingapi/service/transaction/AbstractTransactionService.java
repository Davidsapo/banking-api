package com.fintech.bankingapi.service.transaction;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public abstract class AbstractTransactionService {

    @Autowired
    protected TransactionRepository transactionRepository;

    abstract public TransactionDTO createTransaction(String accountNumber, @Nullable String targetAccountNumber, BigDecimal amount);

    abstract public TransactionType getTransactionType();
}
