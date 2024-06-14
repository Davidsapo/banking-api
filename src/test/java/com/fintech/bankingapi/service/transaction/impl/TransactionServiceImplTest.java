package com.fintech.bankingapi.service.transaction.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.model.entity.Account;
import com.fintech.bankingapi.model.entity.Transaction;
import com.fintech.bankingapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void testCreateTransactionWithoutTargetAccount() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("100.00");
        TransactionType type = TransactionType.DEPOSIT;

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAccount(new Account(accountId));
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO result = transactionService.createTransaction(type, accountId, null, amount);

        assertNotNull(result);
        assertEquals(type, result.getType());
        assertEquals(accountId, result.getAccount().getId());
        assertEquals(amount, result.getAmount());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testCreateTransactionWithTargetAccount() {
        UUID accountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("100.00");
        TransactionType type = TransactionType.TRANSFER;

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAccount(new Account(accountId));
        transaction.setTargetAccount(new Account(targetAccountId));
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO result = transactionService.createTransaction(type, accountId, targetAccountId, amount);

        assertNotNull(result);
        assertEquals(type, result.getType());
        assertEquals(accountId, result.getAccount().getId());
        assertEquals(targetAccountId, result.getTargetAccount().getId());
        assertEquals(amount, result.getAmount());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}