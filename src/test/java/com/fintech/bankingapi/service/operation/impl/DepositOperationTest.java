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
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositOperationTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private DepositOperation depositOperation;

    @Test
    void testExecuteOperation() {
        String accountNumber = "1234567890";
        BigDecimal amount = new BigDecimal("100.00");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(UUID.randomUUID());
        accountDTO.setAccountNumber(accountNumber);
        accountDTO.setBalance(amount);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(UUID.randomUUID());
        transactionDTO.setType(TransactionType.DEPOSIT);
        transactionDTO.setAccount(accountDTO);
        transactionDTO.setAmount(amount);
        transactionDTO.setTimestamp(LocalDateTime.now());

        when(accountService.depositBalance(accountNumber, amount)).thenReturn(accountDTO);
        when(transactionService.createTransaction(eq(TransactionType.DEPOSIT), any(UUID.class), isNull(), eq(amount)))
                .thenReturn(transactionDTO);

        TransactionDTO result = depositOperation.executeOperation(accountNumber, null, amount);

        assertNotNull(result);
        assertEquals(TransactionType.DEPOSIT, result.getType());
        assertEquals(accountDTO, result.getAccount());
        assertEquals(amount, result.getAmount());

        verify(accountService, times(1)).depositBalance(accountNumber, amount);
        verify(transactionService, times(1)).createTransaction(eq(TransactionType.DEPOSIT), any(UUID.class), isNull(), eq(amount));
    }
}
