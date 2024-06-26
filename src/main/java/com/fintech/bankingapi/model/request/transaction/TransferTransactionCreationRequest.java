package com.fintech.bankingapi.model.request.transaction;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferTransactionCreationRequest(
        @NotNull(message = "Amount must be provided")
        @Min(value = 0, message = "Amount must be greater than 0") BigDecimal amount,
        @NotBlank(message = "Source account number must be provided") String accountNumber,
        @NotBlank(message = "Target account number must be provided") String targetAccountNumber) {

    @AssertFalse(message = "Source and target account numbers must be different")
    public boolean isDifferentAccountNumbers() {
        return accountNumber.equals(targetAccountNumber);
    }
}
