package com.fintech.bankingapi.api.controller;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.model.request.transaction.DepositTransactionCreationRequest;
import com.fintech.bankingapi.model.request.transaction.TransferTransactionCreationRequest;
import com.fintech.bankingapi.model.request.transaction.WithdrawTransactionCreationRequest;
import com.fintech.bankingapi.service.operation.BankOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final Map<TransactionType, BankOperation> operationMap;

    @Autowired
    public TransactionController(Collection<BankOperation> transactionServices) {
        operationMap = transactionServices.stream()
                .collect(Collectors.toMap(BankOperation::getTransactionType, Function.identity()));
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(@Valid @RequestBody DepositTransactionCreationRequest request) {
        TransactionDTO transaction = operationMap
                .get(DEPOSIT)
                .executeOperation(request.accountNumber(), null, request.amount());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@Valid @RequestBody WithdrawTransactionCreationRequest request) {
        TransactionDTO transaction = operationMap
                .get(WITHDRAW)
                .executeOperation(request.accountNumber(), null, request.amount());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransferTransactionCreationRequest request) {
        TransactionDTO transaction = operationMap
                .get(TRANSFER)
                .executeOperation(request.accountNumber(), request.targetAccountNumber(), request.amount());
        return ResponseEntity.ok(transaction);
    }
}

