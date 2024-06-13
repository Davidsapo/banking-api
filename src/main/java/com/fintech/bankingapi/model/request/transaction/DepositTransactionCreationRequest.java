package com.fintech.bankingapi.model.request.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositTransactionCreationRequest(
        @NotNull(message = "Amount must be provided")
        @Min(value = 0, message = "Amount must be greater than 0") BigDecimal amount,
        @NotBlank(message = "Account number must be provided") String accountNumber) {
}
