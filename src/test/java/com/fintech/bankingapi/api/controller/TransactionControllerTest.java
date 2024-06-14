package com.fintech.bankingapi.api.controller;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.DetailedAccountDTO;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.model.request.acount.AccountCreationRequest;
import com.fintech.bankingapi.model.request.transaction.DepositTransactionCreationRequest;
import com.fintech.bankingapi.model.request.transaction.TransferTransactionCreationRequest;
import com.fintech.bankingapi.model.request.transaction.WithdrawTransactionCreationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accountNumber;
    private String targetAccountNumber;

    @BeforeAll
    public void setupAccounts() {
        AccountCreationRequest sourceRequest = new AccountCreationRequest(new BigDecimal("1000.00"));
        ResponseEntity<DetailedAccountDTO> sourceResponse = restTemplate.postForEntity("/v1/accounts", sourceRequest, DetailedAccountDTO.class);
        assertEquals(HttpStatus.CREATED, sourceResponse.getStatusCode());
        DetailedAccountDTO sourceAccount = sourceResponse.getBody();
        assertNotNull(sourceAccount);
        accountNumber = sourceAccount.getAccountNumber();

        AccountCreationRequest targetRequest = new AccountCreationRequest(new BigDecimal("500.00"));
        ResponseEntity<DetailedAccountDTO> targetResponse = restTemplate.postForEntity("/v1/accounts", targetRequest, DetailedAccountDTO.class);
        assertEquals(HttpStatus.CREATED, targetResponse.getStatusCode());
        DetailedAccountDTO targetAccount = targetResponse.getBody();
        assertNotNull(targetAccount);
        targetAccountNumber = targetAccount.getAccountNumber();
    }

    @Test
    @Order(1)
    public void testDeposit() {
        DepositTransactionCreationRequest request = new DepositTransactionCreationRequest(new BigDecimal("200.00"), accountNumber);

        ResponseEntity<TransactionDTO> response = restTemplate.postForEntity("/v1/transactions/deposit", request, TransactionDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        TransactionDTO transaction = response.getBody();
        assertNotNull(transaction);
        assertEquals(new BigDecimal("200.00"), transaction.getAmount());
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }

    @Test
    @Order(2)
    public void testWithdraw() {
        WithdrawTransactionCreationRequest request = new WithdrawTransactionCreationRequest(new BigDecimal("100.00"), accountNumber);

        ResponseEntity<TransactionDTO> response = restTemplate.postForEntity("/v1/transactions/withdraw", request, TransactionDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        TransactionDTO transaction = response.getBody();
        assertNotNull(transaction);
        assertEquals(new BigDecimal("100.00"), transaction.getAmount());
        assertEquals(TransactionType.WITHDRAW, transaction.getType());
    }

    @Test
    @Order(3)
    public void testTransfer() {
        TransferTransactionCreationRequest request = new TransferTransactionCreationRequest(new BigDecimal("150.00"), accountNumber, targetAccountNumber);

        ResponseEntity<TransactionDTO> response = restTemplate.postForEntity("/v1/transactions/transfer", request, TransactionDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        TransactionDTO transaction = response.getBody();
        assertNotNull(transaction);
        assertEquals(new BigDecimal("150.00"), transaction.getAmount());
        assertEquals(TransactionType.TRANSFER, transaction.getType());
    }
}
