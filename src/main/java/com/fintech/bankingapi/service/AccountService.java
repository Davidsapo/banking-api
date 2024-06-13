package com.fintech.bankingapi.service;

import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.dto.DetailedAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface AccountService {

    AccountDTO createAccount(BigDecimal initialBalance);

    DetailedAccountDTO getAccountByAccountNumber(String accountNumber);

    Page<AccountDTO> getAllAccounts(Pageable pageable);
}

