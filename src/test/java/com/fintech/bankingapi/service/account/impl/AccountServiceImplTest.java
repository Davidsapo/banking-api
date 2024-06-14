package com.fintech.bankingapi.service.account.impl;

import com.fintech.bankingapi.exceptions.impl.InsufficientFundsException;
import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.dto.DetailedAccountDTO;
import com.fintech.bankingapi.model.entity.Account;
import com.fintech.bankingapi.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testCreateAccount() {
        BigDecimal initialBalance = new BigDecimal("100.00");
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setAccountNumber("1234567890");
        account.setBalance(initialBalance);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        DetailedAccountDTO result = accountService.createAccount(initialBalance);

        assertNotNull(result);
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(initialBalance, result.getBalance());

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testGetAccountByAccountNumber() {
        String accountNumber = "1234567890";
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        DetailedAccountDTO result = accountService.getAccountByAccountNumber(accountNumber);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    void testGetAllAccounts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        accounts.add(account);
        Page<Account> accountPage = new PageImpl<>(accounts, pageable, accounts.size());

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);

        Page<AccountDTO> result = accountService.getAllAccounts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        verify(accountRepository, times(1)).findAll(pageable);
    }

    @Test
    void testDepositBalance() {
        String accountNumber = "1234567890";
        BigDecimal amount = new BigDecimal("50.00");
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("100.00"));

        when(accountRepository.findAndLockByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO result = accountService.depositBalance(accountNumber, amount);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(new BigDecimal("150.00"), result.getBalance());

        verify(accountRepository, times(1)).findAndLockByAccountNumber(accountNumber);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testWithdrawBalance() {
        String accountNumber = "1234567890";
        BigDecimal amount = new BigDecimal("50.00");
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("100.00"));

        when(accountRepository.findAndLockByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO result = accountService.withdrawBalance(accountNumber, amount);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(new BigDecimal("50.00"), result.getBalance());

        verify(accountRepository, times(1)).findAndLockByAccountNumber(accountNumber);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testWithdrawBalanceInsufficientFunds() {
        String accountNumber = "1234567890";
        BigDecimal amount = new BigDecimal("150.00");
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("100.00"));

        when(accountRepository.findAndLockByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> accountService.withdrawBalance(accountNumber, amount));

        verify(accountRepository, times(1)).findAndLockByAccountNumber(accountNumber);
        verify(accountRepository, times(0)).save(any(Account.class));
    }
}

