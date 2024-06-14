package com.fintech.bankingapi.service.operation.impl;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.service.account.AccountService;
import com.fintech.bankingapi.service.transaction.TransactionService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferOperationTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransferOperation transferOperation;

    @Test
    void testExecuteOperation() {
        String accountNumber = "1234567890";
        String targetAccountNumber = "0987654321";
        BigDecimal amount = new BigDecimal("100.00");

        AccountDTO sourceAccountDTO = new AccountDTO();
        sourceAccountDTO.setId(UUID.randomUUID());
        sourceAccountDTO.setAccountNumber(accountNumber);
        sourceAccountDTO.setBalance(new BigDecimal("900.00"));

        AccountDTO targetAccountDTO = new AccountDTO();
        targetAccountDTO.setId(UUID.randomUUID());
        targetAccountDTO.setAccountNumber(targetAccountNumber);
        targetAccountDTO.setBalance(new BigDecimal("1100.00"));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(UUID.randomUUID());
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setAccount(sourceAccountDTO);
        transactionDTO.setTargetAccount(targetAccountDTO);
        transactionDTO.setAmount(amount);
        transactionDTO.setTimestamp(LocalDateTime.now());

        when(accountService.withdrawBalance(accountNumber, amount)).thenReturn(sourceAccountDTO);
        when(accountService.depositBalance(targetAccountNumber, amount)).thenReturn(targetAccountDTO);
        when(transactionService.createTransaction(eq(TransactionType.TRANSFER), any(UUID.class), any(UUID.class), eq(amount)))
                .thenReturn(transactionDTO);

        TransactionDTO result = transferOperation.executeOperation(accountNumber, targetAccountNumber, amount);

        assertNotNull(result);
        assertEquals(TransactionType.TRANSFER, result.getType());
        assertEquals(sourceAccountDTO, result.getAccount());
        assertEquals(targetAccountDTO, result.getTargetAccount());
        assertEquals(amount, result.getAmount());

        verify(accountService, times(1)).withdrawBalance(accountNumber, amount);
        verify(accountService, times(1)).depositBalance(targetAccountNumber, amount);
        verify(transactionService, times(1)).createTransaction(eq(TransactionType.TRANSFER), any(UUID.class), any(UUID.class), eq(amount));
    }
}
