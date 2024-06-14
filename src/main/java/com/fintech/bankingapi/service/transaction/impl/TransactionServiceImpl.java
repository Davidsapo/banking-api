package com.fintech.bankingapi.service.transaction.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.model.entity.Account;
import com.fintech.bankingapi.model.entity.Transaction;
import com.fintech.bankingapi.repository.TransactionRepository;
import com.fintech.bankingapi.service.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static com.fintech.bankingapi.converter.TransactionConverter.convertToDTO;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    protected TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionType type, UUID accountId, @Nullable UUID targetAccountId, BigDecimal amount) {
        Transaction depositTransaction = new Transaction();
        depositTransaction.setAccount(new Account(accountId));
        if (nonNull(targetAccountId)) {
            depositTransaction.setTargetAccount(new Account(targetAccountId));
        }
        depositTransaction.setType(type);
        depositTransaction.setAmount(amount);

        transactionRepository.save(depositTransaction);
        log.info("{} transaction created for account id {}: amount = {}", type, accountId, amount);
        return convertToDTO(depositTransaction);
    }
}
