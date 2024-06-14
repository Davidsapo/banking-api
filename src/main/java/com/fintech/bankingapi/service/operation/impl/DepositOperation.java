package com.fintech.bankingapi.service.operation.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.operation.BankOperation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.fintech.bankingapi.enums.TransactionType.DEPOSIT;

@Component
public class DepositOperation extends BankOperation {

    @Override
    public TransactionType getTransactionType() {
        return DEPOSIT;
    }

    @Override
    @Transactional
    public TransactionDTO executeOperation(String accountNumber, @Nullable String targetAccountNumber, BigDecimal amount) {
        AccountDTO account = accountService.depositBalance(accountNumber, amount);
        TransactionDTO transaction = transactionService.createTransaction(getTransactionType(), account.getId(), null, amount);
        transaction.setAccount(account);
        return transaction;
    }
}
