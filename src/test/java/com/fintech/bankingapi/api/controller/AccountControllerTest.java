package com.fintech.bankingapi.api.controller;

import com.fintech.bankingapi.model.dto.DetailedAccountDTO;
import com.fintech.bankingapi.model.request.acount.AccountCreationRequest;
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
public class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accountNumber;

    @Test
    @Order(1)
    public void testCreateAccount() {
        AccountCreationRequest request = new AccountCreationRequest(new BigDecimal("1000.00"));

        ResponseEntity<DetailedAccountDTO> response = restTemplate.postForEntity("/v1/accounts", request, DetailedAccountDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        DetailedAccountDTO account = response.getBody();
        assertNotNull(account);
        assertEquals(new BigDecimal("1000.00"), account.getBalance());

        accountNumber = account.getAccountNumber();
    }

    @Test
    @Order(2)
    public void testGetAccountByAccountNumber() {
        ResponseEntity<DetailedAccountDTO> response = restTemplate.getForEntity("/v1/accounts/" + accountNumber, DetailedAccountDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        DetailedAccountDTO account = response.getBody();
        assertNotNull(account);
        assertEquals(accountNumber, account.getAccountNumber());
    }
}
