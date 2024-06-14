package com.fintech.bankingapi.service.operation;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.account.AccountService;
import com.fintech.bankingapi.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public abstract class BankOperation {

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected TransactionService transactionService;

    public abstract TransactionType getTransactionType();

    public abstract TransactionDTO executeOperation(String accountNumber, @Nullable String targetAccountNumber, BigDecimal amount);
}
