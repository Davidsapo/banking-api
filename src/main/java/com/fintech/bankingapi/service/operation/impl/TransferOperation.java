package com.fintech.bankingapi.service.operation.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.operation.BankOperation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.fintech.bankingapi.enums.TransactionType.TRANSFER;

@Component
public class TransferOperation extends BankOperation {

    @Override
    public TransactionType getTransactionType() {
        return TRANSFER;
    }

    @Override
    @Transactional
    public TransactionDTO executeOperation(String accountNumber, String targetAccountNumber, BigDecimal amount) {
        AccountDTO sourceAccount = accountService.withdrawBalance(accountNumber, amount);
        AccountDTO targetAccount = accountService.depositBalance(targetAccountNumber, amount);
        return transactionService.createTransaction(getTransactionType(), sourceAccount.getId(), targetAccount.getId(), amount);
    }
}
