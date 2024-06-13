package com.fintech.bankingapi.model.request.acount;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreationRequest(
        @NotNull(message = "Initial balance is required")
        @Min(value = 0, message = "Initial balance must be non-negative")
        BigDecimal initialBalance) {
}
