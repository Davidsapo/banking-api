package com.fintech.bankingapi.api.controller;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.model.request.transaction.DepositTransactionCreationRequest;
import com.fintech.bankingapi.model.request.transaction.TransferTransactionCreationRequest;
import com.fintech.bankingapi.model.request.transaction.WithdrawTransactionCreationRequest;
import com.fintech.bankingapi.service.transaction.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.fintech.bankingapi.enums.TransactionType.DEPOSIT;
import static com.fintech.bankingapi.enums.TransactionType.TRANSFER;
import static com.fintech.bankingapi.enums.TransactionType.WITHDRAW;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private final Map<TransactionType, TransactionService> transactionServiceMap;

    @Autowired
    public TransactionController(Collection<TransactionService> transactionServices) {
        transactionServiceMap = transactionServices.stream()
                .collect(Collectors.toMap(TransactionService::getTransactionType, Function.identity()));
    }

    @PostMapping("/deposit")
    public TransactionDTO deposit(@Valid @RequestBody DepositTransactionCreationRequest request) {
        return transactionServiceMap
                .get(DEPOSIT)
                .createTransaction(request.accountNumber(), null, request.amount());
    }

    @PostMapping("/withdraw")
    public TransactionDTO withdraw(@Valid @RequestBody WithdrawTransactionCreationRequest request) {
        return transactionServiceMap
                .get(WITHDRAW)
                .createTransaction(request.accountNumber(), null, request.amount());
    }

    @PostMapping("/transfer")
    public TransactionDTO transfer(@Valid @RequestBody TransferTransactionCreationRequest request) {
        return transactionServiceMap
                .get(TRANSFER)
                .createTransaction(request.accountNumber(), request.targetAccountNumber(), request.amount());
    }
}

