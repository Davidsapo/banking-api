package com.fintech.bankingapi.api.controller;

import com.fintech.bankingapi.model.PaginatedResponse;
import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.request.acount.AccountCreationRequest;
import com.fintech.bankingapi.service.account.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountCreationRequest request) {
        AccountDTO account = accountService.createAccount(request.initialBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByAccountNumber(@PathVariable @NotBlank String accountNumber) {
        AccountDTO account = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<AccountDTO>> getAllAccounts(Pageable pageable) {
        Page<AccountDTO> accounts = accountService.getAllAccounts(pageable);
        PaginatedResponse<AccountDTO> response = new PaginatedResponse<>(accounts);
        return ResponseEntity.ok(response);
    }
}

