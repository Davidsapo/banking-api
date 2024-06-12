package com.fintech.bankingapi.service.impl;

import com.fintech.bankingapi.converter.AccountConverter;
import com.fintech.bankingapi.exceptions.impl.AccountNotFoundException;
import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.entity.Account;
import com.fintech.bankingapi.model.entity.Transaction;
import com.fintech.bankingapi.repository.AccountRepository;
import com.fintech.bankingapi.service.AccountService;
import com.fintech.bankingapi.utils.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fintech.bankingapi.converter.AccountConverter.convertToDTO;
import static com.fintech.bankingapi.converter.AccountConverter.convertToDTOs;
import static com.fintech.bankingapi.enums.TransactionType.DEPOSIT;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountDTO createAccount(BigDecimal initialBalance) {
        Account account = initializeAccount(initialBalance);

        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            createInitialTransaction(account, initialBalance);
        }

        account = accountRepository.save(account);
        return convertToDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(AccountConverter::convertToDTO)
                .orElseThrow(() -> new AccountNotFoundException(Map.of("accountNumber", accountNumber)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountDTO> getAllAccounts(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<AccountDTO> accountDTOs = convertToDTOs(accountPage.getContent());
        return new PageImpl<>(accountDTOs, pageable, accountPage.getTotalElements());
    }

    /* Utility methods */

    private Account initializeAccount(BigDecimal initialBalance) {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setAccountNumber(AccountNumberGenerator.generateAccountNumber());
        account.setBalance(initialBalance);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }

    private void createInitialTransaction(Account account, BigDecimal initialBalance) {
        Transaction initialTransaction = new Transaction();
        initialTransaction.setId(UUID.randomUUID());
        initialTransaction.setAccount(account);
        initialTransaction.setType(DEPOSIT);
        initialTransaction.setAmount(initialBalance);
        initialTransaction.setTimestamp(LocalDateTime.now());

        account.getTransactions().add(initialTransaction);
    }
}
