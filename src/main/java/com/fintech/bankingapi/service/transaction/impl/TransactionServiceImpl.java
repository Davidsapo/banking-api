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
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fintech.bankingapi.converter.TransactionConverter.convertToDTO;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionType type, UUID accountId, @Nullable UUID targetAccountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(new Account(accountId));
        if (nonNull(targetAccountId)) {
            transaction.setTargetAccount(new Account(targetAccountId));
        }
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);
        log.info("{} transaction created for account id {}: amount = {}", type, accountId, amount);
        return convertToDTO(transaction);
    }
}
